package edu.ucan.sdp2.bancocore.services;


import edu.ucan.sdp2.bancocore.dto.Resposta;
import edu.ucan.sdp2.bancocore.dto.requisicoes.EntidadeRequisicaoAbstract;
import edu.ucan.sdp2.bancocore.dto.requisicoes.TransacaoDepositoRequisicao;
import edu.ucan.sdp2.bancocore.dto.requisicoes.TransacaoTransferenciaRequisicao;
import edu.ucan.sdp2.bancocore.entities.Movimento;
import edu.ucan.sdp2.bancocore.entities.Transacao;
import edu.ucan.sdp2.bancocore.entities.Utilizador;
import edu.ucan.sdp2.bancocore.enums.TipoMovimento;
import edu.ucan.sdp2.bancocore.mapper.MovimentoMapper;
import edu.ucan.sdp2.bancocore.repositories.ContaBancariaRepository;
import edu.ucan.sdp2.bancocore.repositories.TransacaoRepository;
import edu.ucan.sdp2.bancocore.utils.ManipuladorContaUtil;
import edu.ucan.sdp2.bancocore.utils.SessaoRequisicao;
import edu.ucan.sdp2.conectacore.banco.ConectaEmissorBancoService;
import edu.ucan.sdp2.conectacore.models.TransacaoConecta;
import edu.ucan.sdp2.conectacore.models.TransacaoConectaDetalhes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TransacaoService extends ServicoGenerico<Transacao, Transacao> {
    @Override
    protected Transacao mapearResposta(Transacao model) {
        return model;
    }

    @Override
    public ResponseEntity<Resposta> criar(EntidadeRequisicaoAbstract<Transacao> entityRequest) {
        return null;
    }

    @Override
    public ResponseEntity<Resposta> actualizar(UUID id, EntidadeRequisicaoAbstract<Transacao> entityRequest) {
        return null;
    }

    @Override
    public ResponseEntity<Resposta> deletar(UUID id) {
        return null;
    }

    private final MovimentoService movimentoService;
    private final ManipuladorContaUtil manipuladorContaUtil;
    private final ContaBancariaRepository contaBancariaRepository;

    private final ConectaEmissorBancoService conectaService;

    @Autowired
    public TransacaoService(MovimentoService movimentoService, ManipuladorContaUtil manipuladorContaUtil, ContaBancariaRepository contaBancariaRepository, TransacaoRepository repository, ConectaEmissorBancoService conectaService){
        this.movimentoService = movimentoService;
        this.manipuladorContaUtil = manipuladorContaUtil;
        this.contaBancariaRepository = contaBancariaRepository;
        this.repository = repository;
        this.conectaService = conectaService;
    }

    public ResponseEntity<Resposta> transferir(TransacaoTransferenciaRequisicao transferenciaRequisicao) {
        if (!transferenciaRequisicao.isValido()){
            return new Resposta<>(transferenciaRequisicao.getMensagemErro(),null).recusado();
        }
        var transferencia = transferenciaRequisicao.mapearEntidade();
        var resposta = movimentoService.movimentarConta(transferencia.getMovimento());
        var movimentoRealizado = resposta.getDados();
        if (movimentoRealizado == null) {
            return resposta.recusado();
        }
        var contaOrigem = contaBancariaRepository.findById(movimentoRealizado.getConta().getId()).orElse(null);
        if (contaOrigem == null) {
            movimentoService.reverterMovimento(movimentoRealizado);
            return new Resposta<>("Houve um problema com a sua conta de origem", null).error();
        }
        movimentoRealizado.setConta(contaOrigem);
        transferencia.setOperador(SessaoRequisicao.utilizador);
        if (transferencia.getContaDestino().startsWith(manipuladorContaUtil.getIban())) {
            var contaDestino = contaBancariaRepository.findFirstByIbanConta(transferencia.getContaDestino());
            if (contaDestino == null) {
                movimentoService.reverterMovimento(movimentoRealizado);
                return new Resposta<>("A conta que pretende efetuar a transferência não existe! Por favor, verifique o IBAN", null).error();
            }else {
                movimentoService.creditarConta(contaDestino, transferenciaRequisicao.getValor());
                movimentoService.actualizarSaldoContabilistico(movimentoRealizado);
                var novaTransferencia = repository.save(transferencia);
                String nomeRepresentantes = contaDestino.getRepresentantes().stream().map(Utilizador::getNomeCompleto).collect(Collectors.joining(", "));
                return new Resposta<>(String.format("Nova transferência efetuada para %s, representando a conta %s ", nomeRepresentantes, contaDestino.getNumeroConta().toUpperCase()), novaTransferencia).sucesso();
            }
        }else {
            boolean isEnviado = conectaService.enviarMovimento(
                        new TransacaoConectaDetalhes(
                                transferencia.getContaDestino(),
                                transferencia.getMovimento().getValorMovimento(),
                                TipoMovimento.Credito.toString()
                        )
                    );
            if (!isEnviado) {
                movimentoService.reverterMovimento(resposta.getDados());
                return new Resposta<>("A operação não foi enviada por erro na conciliação de dados",null) .error();
            }

            var novaTransferencia = repository.save(transferencia);
            return new Resposta<>(String.format("Nova transferência efetuada para o IBAN: %s ", novaTransferencia.getContaDestino()), novaTransferencia).sucesso();
        }
    }



    public ResponseEntity<Resposta> depositar(TransacaoDepositoRequisicao depsitarTransacao) {
        if (!depsitarTransacao.isValido()){
            return new Resposta<>(depsitarTransacao.getMensagemErro(),null).recusado();
        }
        var deposito = depsitarTransacao.mapearEntidade();

        deposito.setOperador(SessaoRequisicao.utilizador);
        var contaDestino = contaBancariaRepository.findFirstByNumeroContaOrIbanConta(deposito.getContaDestino(), deposito.getContaDestino());
        if (contaDestino == null) {
            return new Resposta<>("A conta que pretende efetuar o depósito não existe! Por favor, verifique o IBAN ou o Número de Conta", null).error();
        }else {
            deposito.getMovimento().setConta(contaDestino);
            var resposta = movimentoService.creditarConta(contaDestino, depsitarTransacao.getValor());
            movimentoService.actualizarSaldoContabilistico(resposta.getDados());
            deposito.setContaDestino(contaDestino.getNumeroConta());
            deposito.setMovimento(resposta.getDados());
            var novaTransferencia = repository.save(deposito);
            String nomeRepresentantes = contaDestino.getRepresentantes().stream().map(Utilizador::getNomeCompleto).collect(Collectors.joining(", "));
            return new Resposta<>(String.format("Novo depósito efetuada para %s, representando a conta %s ", nomeRepresentantes, contaDestino.getNumeroConta().toUpperCase()), novaTransferencia).sucesso();
        }
    }

    public ResponseEntity<Resposta> historicoTransacao(){
        var transacaos = getTransacaoRepository().findAllByOperadorOrderByDataCriacaoDesc(SessaoRequisicao.utilizador);
        if (transacaos == null) {
            return new Resposta<>("Não há Transações feitas por você", null).naoEncontrado();
        }
        return new Resposta<>("Transações carregadas com sucesso", transacaos).sucesso();

    }

    private TransacaoRepository getTransacaoRepository(){
        return ((TransacaoRepository)repository);
    }
}

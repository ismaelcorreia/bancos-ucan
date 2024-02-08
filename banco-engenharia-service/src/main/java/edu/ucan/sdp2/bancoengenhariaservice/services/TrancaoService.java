package edu.ucan.sdp2.bancoengenhariaservice.services;

import edu.ucan.sdp2.bancoengenhariaservice.dto.Resposta;
import edu.ucan.sdp2.bancoengenhariaservice.dto.requisicoes.EntidadeRequisicaoAbstract;
import edu.ucan.sdp2.bancoengenhariaservice.dto.requisicoes.MovimentoRequisicaoDto;
import edu.ucan.sdp2.bancoengenhariaservice.dto.requisicoes.TransacaoTransferenciaRequisicao;
import edu.ucan.sdp2.bancoengenhariaservice.enums.TipoMovimento;
import edu.ucan.sdp2.bancoengenhariaservice.models.Movimento;
import edu.ucan.sdp2.bancoengenhariaservice.models.Transacao;
import edu.ucan.sdp2.bancoengenhariaservice.models.Utilizador;
import edu.ucan.sdp2.bancoengenhariaservice.repositories.ContaBancariaRepository;
import edu.ucan.sdp2.bancoengenhariaservice.repositories.TransacaoRepository;
import edu.ucan.sdp2.bancoengenhariaservice.utils.ManipuladorContaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TrancaoService{

    private final MovimentoService movimentoService;
    private final ManipuladorContaUtil manipuladorContaUtil;
    private final ContaBancariaRepository contaBancariaRepository;
    private final TransacaoRepository repository;

    private final ConectaService conectaService;

    @Autowired
    public TrancaoService(MovimentoService movimentoService, ManipuladorContaUtil manipuladorContaUtil, ContaBancariaRepository contaBancariaRepository, TransacaoRepository repository, ConectaService conectaService){
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
        if (resposta.getDados() == null) {
            return resposta.recusado();
        }

        if (transferencia.getContaDestino().startsWith(manipuladorContaUtil.getIban())) {
            var contaDestino = contaBancariaRepository.findFirstByIbanConta(transferencia.getContaDestino());
            if (contaDestino == null) {
                movimentoService.reverterMovimento(resposta.getDados());
                return new Resposta<>("A conta que pretende efetuar a transferência não existe! Por favor, verifique o IBAN", null).error();
            }else {
                movimentoService.creditarConta(contaDestino, transferenciaRequisicao.getValor());
                movimentoService.actualizarSaldoContabilistico(resposta.getDados());
                var novaTransferencia = repository.save(transferencia);
                String nomeRepresentantes = contaDestino.getRepresentantes().stream().map(Utilizador::getNomeCompleto).collect(Collectors.joining(", "));
                return new Resposta<>(String.format("Nova transferência efetuada para %s, representando a conta %s ", nomeRepresentantes, contaDestino.getNumeroConta().toUpperCase()), novaTransferencia).sucesso();
            }
        }else {
            var requisicaoDtoResposta = conectaService.enviarMovimento(
                    new MovimentoRequisicaoDto(transferencia.getContaDestino(),
                            transferencia.getMovimento().getValorMovimento(),
                            TipoMovimento.Credito));
            if (requisicaoDtoResposta.getDados() == null) {
                movimentoService.reverterMovimento(resposta.getDados());
                return requisicaoDtoResposta.error();
            }
            var novaTransferencia = repository.save(transferencia);
            return new Resposta<>(String.format("Nova transferência efetuada para o IBAN: %s ", novaTransferencia.getContaDestino()), novaTransferencia).sucesso();

        }
    }


}

package edu.ucan.sdp2.bancocore.services;


import edu.ucan.sdp2.bancocore.dto.Resposta;
import edu.ucan.sdp2.bancocore.dto.requisicoes.EntidadeRequisicaoAbstract;
import edu.ucan.sdp2.bancocore.dto.respostas.MovimentosRespotaDto;
import edu.ucan.sdp2.bancocore.entities.ContaBancaria;
import edu.ucan.sdp2.bancocore.entities.Movimento;
import edu.ucan.sdp2.bancocore.enums.TipoMovimento;
import edu.ucan.sdp2.bancocore.mapper.MovimentoMapper;
import edu.ucan.sdp2.bancocore.repositories.ContaBancariaRepository;
import edu.ucan.sdp2.bancocore.repositories.MovimentoRepository;
import edu.ucan.sdp2.conectacore.models.TransacaoConecta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MovimentoService extends ServicoGenerico<Movimento, MovimentosRespotaDto>{



    private final ContaBancariaRepository contaBancariaRepository;
    @Autowired
    public MovimentoService(MovimentoRepository repository, ContaBancariaRepository contaBancariaRepository, MovimentoRepository repository1){
        super.repository = repository;
        this.contaBancariaRepository = contaBancariaRepository;
    }



    @Override
    protected MovimentosRespotaDto mapearResposta(Movimento model) {
        MovimentosRespotaDto movimentoResposta = new MovimentosRespotaDto();
        movimentoResposta.setTipoMovimento(model.getTipoMovimento());
        movimentoResposta.setData(model.getDataCriacao());
        movimentoResposta.setValor(model.getValorMovimento());
        movimentoResposta.setValorAnterior(model.getSaldoAnterior());
        movimentoResposta.setValorPosterir(model.getSaldoDepois());
        movimentoResposta.setId(model.getId());
        return movimentoResposta;
    }

    @Override
    public ResponseEntity<Resposta> criar(EntidadeRequisicaoAbstract<Movimento> entityRequest) {
        return null;
    }

    @Override
    public ResponseEntity<Resposta> actualizar(UUID id, EntidadeRequisicaoAbstract<Movimento> entityRequest) {
        return null;
    }

    @Override
    public ResponseEntity<Resposta> deletar(UUID id) {
        return null;
    }

    public Resposta<Movimento> movimentarConta(Movimento movimento) {
        ContaBancaria contaBancaria = contaBancariaRepository.findById(movimento.getConta().getId()).orElse(null);
        if(contaBancaria == null) {
            return new Resposta<>("Não reconhecemos nenhuma conta com este ID", null);
        }
        double valorDepois = 0;
        if (movimento.getTipoMovimento().equals(TipoMovimento.Debito)) {
            valorDepois = contaBancaria.getSaldoDisponivel() - movimento.getValorMovimento();
            if (valorDepois < 0) {
                return new Resposta<>("O saldo da sua conta é insuficiente para realizar a operação", null);
            }
        }else {
            valorDepois = contaBancaria.getSaldoDisponivel() + movimento.getValorMovimento();
        }
        movimento.setSaldoAnterior(contaBancaria.getSaldoDisponivel());
        movimento.setSaldoDepois(valorDepois);
        contaBancaria.setSaldoDisponivel(valorDepois);
        movimento.setConta(contaBancariaRepository.save(contaBancaria));
        return new Resposta<>("Movimento bem sucedido.", repository.save(movimento));
    }


    public Resposta<Movimento> movimentarContaPorIban(Movimento movimento) {
        ContaBancaria contaBancaria = contaBancariaRepository.findFirstByIbanConta(movimento.getConta().getIbanConta());
        if(contaBancaria == null) {
            return new Resposta<>("Não reconhecemos nenhuma conta com este IBAN", null);
        }
        double valorDepois;
        if (movimento.getTipoMovimento().equals(TipoMovimento.Debito)) {
            valorDepois = contaBancaria.getSaldoDisponivel() - movimento.getValorMovimento();
            if (valorDepois < 0) {
                return new Resposta<>("O saldo da sua conta é insuficiente para realizar a operação", null);
            }
        }else {
            valorDepois = contaBancaria.getSaldoDisponivel() + movimento.getValorMovimento();
        }
        movimento.setSaldoAnterior(contaBancaria.getSaldoDisponivel());
        movimento.setSaldoDepois(valorDepois);
        contaBancaria.setSaldoDisponivel(valorDepois);
        contaBancariaRepository.save(contaBancaria);
        return new Resposta<>("Movimento bem sucedido.", repository.save(movimento));
    }



    // TODO:
    public Resposta<Movimento> movimentarContaPorTransacao(TransacaoConecta dto) {
        if(!dto.isValido()) {
            return new Resposta<>("Dados da transação mal formatados", null);
        }
        Movimento movimento = MovimentoMapper.transacaoConectaDetalhesParaMovimentos(dto.getDetalhes());

        ContaBancaria contaBancaria =  contaBancariaRepository.findFirstByIbanConta(movimento.getConta().getIbanConta());
        if (contaBancaria == null) {
            return new Resposta<>("A conta não foi encontrada", null);
        }
        movimento.setConta(contaBancaria);
        return movimentarConta(movimento);
    }



    public void actualizarSaldoContabilistico(Movimento movimento) {
        var contaBancaria = movimento.getConta();
        double valor;
        if (movimento.getTipoMovimento().equals(TipoMovimento.Debito)) {
            valor = contaBancaria.getSaldoContabilistico() - movimento.getValorMovimento();
        }else {
            valor = contaBancaria.getSaldoContabilistico() + movimento.getValorMovimento();
        }
        contaBancaria.setSaldoContabilistico(valor);
        contaBancariaRepository.save(contaBancaria);
    }



    public void reverterMovimento(Movimento movimento) {
        if (movimento.getTipoMovimento().equals(TipoMovimento.Debito)) {
            movimento.setTipoMovimento(TipoMovimento.Credito);
        }else {
            movimento.setTipoMovimento(TipoMovimento.Debito);
        }
        movimentarConta(movimento);
        actualizarSaldoContabilistico(movimento);
    }


    public Resposta<Movimento> debitarConta(ContaBancaria contaBancaria, double valor) {
        Movimento movimento = new Movimento();
        movimento.setTipoMovimento(TipoMovimento.Debito);
        movimento.setValorMovimento(valor);
        movimento.setConta(contaBancaria);
        var resposta = movimentarConta(movimento);
        if (resposta.getDados() != null) {
            actualizarSaldoContabilistico(movimento);
        }
        return resposta;
    }

    public Resposta<Movimento> creditarContaPorIban(Movimento movimento) {
        ContaBancaria contaBancaria = contaBancariaRepository.findFirstByIbanConta(movimento.getConta().getIbanConta());

        if(contaBancaria == null) {
            return new Resposta<>("Não reconhecemos nenhuma conta com este IBAN", null);
        }
        double valorDepois;
        if (movimento.getTipoMovimento().equals(TipoMovimento.Debito)) {
            valorDepois = contaBancaria.getSaldoDisponivel() - movimento.getValorMovimento();
            if (valorDepois < 0) {
                return new Resposta<>("O saldo da sua conta é insuficiente para realizar a operação", null);
            }
        }else {
            valorDepois = contaBancaria.getSaldoDisponivel() + movimento.getValorMovimento();
        }
        movimento.setSaldoAnterior(contaBancaria.getSaldoDisponivel());
        movimento.setSaldoDepois(valorDepois);
        contaBancaria.setSaldoDisponivel(valorDepois);
        contaBancariaRepository.save(contaBancaria);
        return new Resposta<>("Movimento bem sucedido.", repository.save(movimento));
    }
    public Resposta<Movimento> creditarConta(ContaBancaria contaBancaria, double valor) {
        Movimento movimento = new Movimento();
        movimento.setTipoMovimento(TipoMovimento.Credito);
        movimento.setValorMovimento(valor);
        movimento.setConta(contaBancaria);
        var resposta = movimentarConta(movimento);
        if (resposta.getDados() != null) {
            actualizarSaldoContabilistico(resposta.getDados());
        }
        return resposta;
    }

    public ResponseEntity<Resposta> historicoMovimentos(UUID contaId){
        var conta = contaBancariaRepository.findById(contaId).orElse(null);
        if (conta == null) {
            return new Resposta<>("Não encontramos nenhuma conta com este ID", null).naoEncontrado();
        }
        var movimentos = getMovimentoRepository().findAllByContaOrderByDataCriacaoDesc(conta);

        if (movimentos == null) {
            return new Resposta<>("Não há movimentos para esta conta", null).naoEncontrado();
        }
        return new Resposta<>("Movimentos carregados com sucesso", movimentos).sucesso();
    }

    MovimentoRepository getMovimentoRepository() {
        return (MovimentoRepository) repository;
    }

}

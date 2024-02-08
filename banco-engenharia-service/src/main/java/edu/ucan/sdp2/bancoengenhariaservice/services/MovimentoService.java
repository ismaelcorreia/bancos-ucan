package edu.ucan.sdp2.bancoengenhariaservice.services;

import edu.ucan.sdp2.bancoengenhariaservice.dto.Resposta;
import edu.ucan.sdp2.bancoengenhariaservice.dto.requisicoes.EntidadeRequisicaoAbstract;
import edu.ucan.sdp2.bancoengenhariaservice.dto.requisicoes.MovimentoRequisicaoDto;
import edu.ucan.sdp2.bancoengenhariaservice.enums.TipoMovimento;
import edu.ucan.sdp2.bancoengenhariaservice.models.ContaBancaria;
import edu.ucan.sdp2.bancoengenhariaservice.models.Movimento;
import edu.ucan.sdp2.bancoengenhariaservice.models.Transacao;
import edu.ucan.sdp2.bancoengenhariaservice.repositories.ContaBancariaRepository;
import edu.ucan.sdp2.bancoengenhariaservice.repositories.MovimentoRepository;
import edu.ucan.sdp2.bancoengenhariaservice.repositories.TransacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MovimentoService{

    private final ContaBancariaRepository contaBancariaRepository;
    private final MovimentoRepository repository;
    @Autowired
    public MovimentoService(MovimentoRepository repository, ContaBancariaRepository contaBancariaRepository){
        this.contaBancariaRepository = contaBancariaRepository;
        this.repository = repository;
    }


    public Resposta<Movimento> movimentarConta(Movimento movimento) {
        ContaBancaria contaBancaria = contaBancariaRepository.findById(movimento.getConta().getId()).orElse(null);
        if(contaBancaria == null) {
            return new Resposta<>("Não reconhecemos nenhuma conta com este ID", null);
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

    public Resposta<Movimento> movimentarConta(MovimentoRequisicaoDto dto) {
        if(!dto.isValido()) {
            return new Resposta<>(dto.getMensagemErro(), null);
        }
        Movimento movimento = dto.mapearEntidade();

        ContaBancaria contaBancaria =  contaBancariaRepository.findFirstByIbanConta(movimento.getConta().getIbanConta());
        if (contaBancaria == null) {
            return new Resposta<>("A conta não foi encontrada", null);
        }
        movimento.setConta(contaBancaria);
        return movimentarConta(movimento);
    }

    public void actualizarSaldoContabilistico(Movimento movimento) {
        var contaBancaria = movimento.getConta();
        double saldo = contaBancaria.getSaldoDisponivel();
        contaBancaria.setSaldoContabilistico(saldo);
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

    public Resposta<Movimento> creditarConta(ContaBancaria contaBancaria, double valor) {
        Movimento movimento = new Movimento();
        movimento.setTipoMovimento(TipoMovimento.Credito);
        movimento.setValorMovimento(valor);
        movimento.setConta(contaBancaria);
        var resposta = movimentarConta(movimento);
        if (resposta.getDados() != null) {
            actualizarSaldoContabilistico(movimento);
        }
        return resposta;
    }
    public List<Movimento> listar() {
        return repository.findAll();
    }
    public Page<Movimento> listar(Pageable pageable) {
        return repository.findAll(pageable);
    }

}

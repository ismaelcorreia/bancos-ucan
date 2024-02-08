package edu.ucan.sdp2.bancoengenhariaservice.services;

import edu.ucan.sdp2.bancoengenhariaservice.dto.Resposta;
import edu.ucan.sdp2.bancoengenhariaservice.dto.requisicoes.ContaRequisicaoDto;
import edu.ucan.sdp2.bancoengenhariaservice.dto.requisicoes.EntidadeRequisicaoAbstract;
import edu.ucan.sdp2.bancoengenhariaservice.models.ContaBancaria;
import edu.ucan.sdp2.bancoengenhariaservice.repositories.ContaBancariaRepository;
import edu.ucan.sdp2.bancoengenhariaservice.utils.ManipuladorContaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public class ContaService extends ServicoGenerico<ContaBancaria, ContaBancaria>{

    private final ManipuladorContaUtil manipuladorContaUtil;
    @Autowired
    public ContaService(ContaBancariaRepository contaBancariaRepository, ManipuladorContaUtil manipuladorContaUtil){
        this.manipuladorContaUtil = manipuladorContaUtil;
        this.repository = contaBancariaRepository;
    }

    @Override
    protected ContaBancaria mapearResposta(ContaBancaria model) {
        return model;
    }
    @Override
    public ResponseEntity<Resposta> criar(EntidadeRequisicaoAbstract<ContaBancaria> entityRequest) {
        var contaRequisicao = (ContaRequisicaoDto) entityRequest;
        if (!contaRequisicao.isValido()){
            return new Resposta<>(contaRequisicao.getMensagemErro(), null).recusado();
        }
        var conta = contaRequisicao.mapearEntidade();
        final long proximoNumero = repository.count() + 1;
        conta.setNumeroConta(manipuladorContaUtil.formatarNumeroConta(proximoNumero));
        conta.setIbanConta(manipuladorContaUtil.formatarIBANConta(proximoNumero));
        return new Resposta<>("Conta criada com sucesso", repository.save(conta)).sucesso();
    }

    @Override
    public ResponseEntity<Resposta> actualizar(UUID id, EntidadeRequisicaoAbstract<ContaBancaria> entityRequest) {
        return null;
    }

    @Override
    public ResponseEntity<Resposta> deletar(UUID id) {
        return super.deletarPadrao(id);
    }
}

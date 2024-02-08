package edu.ucan.sdp2.bancoengenhariaservice.services;

import edu.ucan.sdp2.bancoengenhariaservice.dto.Resposta;
import edu.ucan.sdp2.bancoengenhariaservice.dto.requisicoes.ContaRequisicaoDto;
import edu.ucan.sdp2.bancoengenhariaservice.dto.requisicoes.EntidadeRequisicaoAbstract;
import edu.ucan.sdp2.bancoengenhariaservice.models.ContaBancaria;
import edu.ucan.sdp2.bancoengenhariaservice.models.Utilizador;
import edu.ucan.sdp2.bancoengenhariaservice.repositories.ContaBancariaRepository;
import edu.ucan.sdp2.bancoengenhariaservice.repositories.UtilizadorRepository;
import edu.ucan.sdp2.bancoengenhariaservice.utils.ManipuladorContaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Service
public class ContaService extends ServicoGenerico<ContaBancaria, ContaBancaria>{

    private final ManipuladorContaUtil manipuladorContaUtil;
    private final UtilizadorRepository utilizadorRepository;
    @Autowired
    public ContaService(ContaBancariaRepository contaBancariaRepository, ManipuladorContaUtil manipuladorContaUtil, UtilizadorRepository utilizadorRepository){
        this.manipuladorContaUtil = manipuladorContaUtil;
        this.utilizadorRepository = utilizadorRepository;
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
        List<Utilizador> representantesVerificados = new ArrayList<Utilizador>();
        for (Utilizador reprentante : conta.getRepresentantes()) {
            var rep = utilizadorRepository.findById(reprentante.getId()).orElse(null);
            if (rep == null) {
                return new Resposta<>("O representate identificado pelo ID "+reprentante.getId().toString()+" não existe no sistema!", null).recusado();
            }
            representantesVerificados.add(rep);
        }

        conta.setRepresentantes(representantesVerificados);
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

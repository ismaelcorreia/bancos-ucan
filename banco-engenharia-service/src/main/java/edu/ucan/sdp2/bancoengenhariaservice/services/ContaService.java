package edu.ucan.sdp2.bancoengenhariaservice.services;

import edu.ucan.sdp2.bancoengenhariaservice.dto.Resposta;
import edu.ucan.sdp2.bancoengenhariaservice.dto.requisicoes.EntidadeRequisicaoAbstract;
import edu.ucan.sdp2.bancoengenhariaservice.models.ContaBancaria;
import edu.ucan.sdp2.bancoengenhariaservice.repositories.ContaBancariaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public class ContaService extends ServicoGenerico<ContaBancaria, ContaBancaria>{

    @Autowired
    public ContaService(ContaBancariaRepository contaBancariaRepository){
        this.repository = contaBancariaRepository;
    }

    @Override
    protected ContaBancaria mapearResposta(ContaBancaria model) {
        return model;
    }
    @Override
    public ResponseEntity<Resposta> criar(EntidadeRequisicaoAbstract<ContaBancaria> entityRequest) {
        return super.criarPadrao(entityRequest);
    }

    @Override
    public ResponseEntity<Resposta> actualizar(UUID id, EntidadeRequisicaoAbstract<ContaBancaria> entityRequest) {
        return actualizarPadrao(id, entityRequest);
    }

    @Override
    public ResponseEntity<Resposta> deletar(UUID id) {
        return super.deletarPadrao(id);
    }
}

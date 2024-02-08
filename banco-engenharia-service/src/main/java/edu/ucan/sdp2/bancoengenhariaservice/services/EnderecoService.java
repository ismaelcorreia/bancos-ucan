package edu.ucan.sdp2.bancoengenhariaservice.services;

import edu.ucan.sdp2.bancoengenhariaservice.dto.Resposta;
import edu.ucan.sdp2.bancoengenhariaservice.dto.requisicoes.EntidadeRequisicaoAbstract;
import edu.ucan.sdp2.bancoengenhariaservice.models.ContaBancaria;
import edu.ucan.sdp2.bancoengenhariaservice.models.Endereco;
import edu.ucan.sdp2.bancoengenhariaservice.repositories.ContaBancariaRepository;
import edu.ucan.sdp2.bancoengenhariaservice.repositories.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EnderecoService extends ServicoGenerico<Endereco, Endereco>{

    @Autowired
    public EnderecoService(EnderecoRepository enderecoRepository){
        this.repository = enderecoRepository;
    }

    @Override
    protected Endereco mapearResposta(Endereco model) {
        return model;
    }

    @Override
    public ResponseEntity<Resposta> criar(EntidadeRequisicaoAbstract<Endereco> entityRequest) {
        return super.criarPadrao(entityRequest);
    }

    @Override
    public ResponseEntity<Resposta> actualizar(UUID id, EntidadeRequisicaoAbstract<Endereco> entityRequest) {
        return  super.actualizarPadrao(id, entityRequest);
    }

    @Override
    public ResponseEntity<Resposta> deletar(UUID id) {
        return super.deletarPadrao(id);
    }
}

package edu.ucan.sdp2.bancoengenhariaservice.services;

import edu.ucan.sdp2.bancoengenhariaservice.dto.Resposta;
import edu.ucan.sdp2.bancoengenhariaservice.dto.requisicoes.EntidadeRequisicaoAbstract;
import edu.ucan.sdp2.bancoengenhariaservice.models.ContaBancaria;
import edu.ucan.sdp2.bancoengenhariaservice.models.Transacao;
import edu.ucan.sdp2.bancoengenhariaservice.repositories.EnderecoRepository;
import edu.ucan.sdp2.bancoengenhariaservice.repositories.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TrancaoService extends ServicoGenerico<Transacao, Transacao>{


    @Autowired
    public TrancaoService(TransacaoRepository transacaoRepository){
        this.repository = transacaoRepository;
    }


    @Override
    protected Transacao mapearResposta(Transacao model) {
        return model;
    }

    @Override
    public ResponseEntity<Resposta> criar(EntidadeRequisicaoAbstract<Transacao> entityRequest) {
        return super.criarPadrao(entityRequest);
    }

    @Override
    public ResponseEntity<Resposta> actualizar(UUID id, EntidadeRequisicaoAbstract<Transacao> entityRequest) {
        return super.actualizarPadrao(id, entityRequest);
    }

    @Override
    public ResponseEntity<Resposta> deletar(UUID id) {
        return super.deletarPadrao(id);
    }
}

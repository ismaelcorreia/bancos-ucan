package edu.ucan.sdp2.bancocore.controllers;


import edu.ucan.sdp2.bancocore.dto.Resposta;
import edu.ucan.sdp2.bancocore.dto.requisicoes.TransacaoDepositoRequisicao;
import edu.ucan.sdp2.bancocore.dto.requisicoes.TransacaoTransferenciaRequisicao;
import edu.ucan.sdp2.bancocore.dto.respostas.TransacaoRespotaDto;
import edu.ucan.sdp2.bancocore.entities.Transacao;
import edu.ucan.sdp2.bancocore.services.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/transacao")
public class TransacaoController extends ControladorGenerico<Transacao, TransacaoRespotaDto> {


    @Autowired
    public TransacaoController(TransacaoService movimentoService) { this.serviceApi = movimentoService;}

    @GetMapping(value = "/minhas-transacoes")
    public ResponseEntity<Resposta> getTransacoes() {
        return getServiceApi().historicoTransacao();
    }
    private TransacaoService getServiceApi() {
        return (TransacaoService) serviceApi;
    }

    @PostMapping("/transferir")
    public ResponseEntity<Resposta> transferir(@RequestBody TransacaoTransferenciaRequisicao transferenciaRequisicao) {
        return getServiceApi().transferir(transferenciaRequisicao);
    }


    @PostMapping("/transferir-dinheiro")
    public ResponseEntity<Resposta> transferirDinheiro(@RequestBody TransacaoTransferenciaRequisicao transferenciaRequisicao) {
        return getServiceApi().transferir(transferenciaRequisicao);
    }
    @PostMapping("/depositar")
    public ResponseEntity<Resposta> depositar(@RequestBody TransacaoDepositoRequisicao transferenciaRequisicao) {
        return getServiceApi().depositar(transferenciaRequisicao);
    }
}



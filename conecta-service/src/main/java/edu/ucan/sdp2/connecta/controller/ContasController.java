package edu.ucan.sdp2.connecta.controller;

import edu.ucan.sdp2.conectacore.models.TransacaoRequisicao;
import edu.ucan.sdp2.connecta.dto.conta.ContaResposta;
import edu.ucan.sdp2.connecta.dto.transacao.TransacaoStatusResposta;
import edu.ucan.sdp2.connecta.model.Conta;
import edu.ucan.sdp2.connecta.service.ServicoConta;
import edu.ucan.sdp2.connecta.service.TransacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/contas")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ContasController {

    private final ServicoConta service;


    @GetMapping(value = "/{id}", params = "chave")
    Mono<ContaResposta> minhasContas(

            @PathVariable String id,
            @RequestParam String chave
    ) {
        return service.allMine(UUID.fromString(id), chave);
    }
}

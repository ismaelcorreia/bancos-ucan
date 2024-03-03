package edu.ucan.sdp2.connecta.controller;

import edu.ucan.sdp2.conectacore.models.TransacaoRequisicao;
import edu.ucan.sdp2.connecta.dto.conta.TransacaoResposta;
import edu.ucan.sdp2.connecta.dto.transacao.TransacaoStatusResposta;
import edu.ucan.sdp2.connecta.service.ServicoTrasancao;
import edu.ucan.sdp2.connecta.service.TransacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/transacoes")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TransacaoController {

    private final ServicoTrasancao servicoTrasancao;
    private final TransacaoService service;
    @PostMapping(value = "/requisicao/{id}", params = "chave")
    Mono<ResponseEntity<TransacaoStatusResposta>> requisicao(
            @PathVariable String id,
            @RequestParam String chave,
            @RequestBody TransacaoRequisicao requisicao) {
        return service.requisitar(
                UUID.fromString(id),
                chave.toUpperCase(),
                requisicao
        );
    }



    @GetMapping(value = "/{id}", params = "chave")
    Mono<TransacaoResposta> movimentos(

            @PathVariable String id,
            @RequestParam String chave
    ) {
        return servicoTrasancao.transacoes(UUID.fromString(id), chave);
    }

    @GetMapping
    Mono<String> requisicao() {
        return Mono.just("TEste");
    }
}

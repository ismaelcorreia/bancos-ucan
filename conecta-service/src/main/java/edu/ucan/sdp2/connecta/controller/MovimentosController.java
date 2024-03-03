package edu.ucan.sdp2.connecta.controller;

import edu.ucan.sdp2.connecta.dto.conta.ContaResposta;
import edu.ucan.sdp2.connecta.dto.conta.MovimentosResposta;
import edu.ucan.sdp2.connecta.service.ServicoConta;
import edu.ucan.sdp2.connecta.service.ServicoMovimento;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/movimentos")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MovimentosController {

    private final ServicoMovimento service;


    @GetMapping(value = "/{id}", params = "chave")
    Mono<MovimentosResposta> movimentos(

            @PathVariable String id,
            @RequestParam String chave
    ) {
        return service.allMine(UUID.fromString(id), chave);
    }
}

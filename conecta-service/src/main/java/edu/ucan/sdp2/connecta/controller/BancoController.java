package edu.ucan.sdp2.connecta.controller;

import edu.ucan.sdp2.connecta.dto.banco.BancoCadastroRequisicao;
import edu.ucan.sdp2.connecta.dto.banco.BancoResposta;
import edu.ucan.sdp2.connecta.service.BancoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bancos")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BancoController {

    private final BancoService service;
    @PostMapping
    Mono<ResponseEntity<BancoResposta>> requisicao(@RequestBody BancoCadastroRequisicao requisicao) {
        return service.registar(requisicao);
    }
    @GetMapping
    Mono<ResponseEntity<List<BancoResposta>>> findAll() {
        return service.all();
    }
    @GetMapping(value = "/{id}", params = "chave")
    Mono<ResponseEntity<BancoResposta>> dados(@PathVariable String id, @RequestParam String chave) {
        return service.findDados(chave, UUID.fromString(id));
    }

}

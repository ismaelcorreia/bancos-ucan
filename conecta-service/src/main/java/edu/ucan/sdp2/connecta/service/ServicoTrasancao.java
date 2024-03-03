package edu.ucan.sdp2.connecta.service;


import edu.ucan.sdp2.connecta.dto.conta.TransacaoResposta;
import edu.ucan.sdp2.connecta.repo.BancoRepo;
import edu.ucan.sdp2.connecta.utils.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ServicoTrasancao {

    private final BancoRepo bancoRepo;
    public Mono<TransacaoResposta> transacoes(
            UUID id,
            String chave) {

        return  bancoRepo.findById(id).flatMap(banco -> {
            if (banco == null) {
                throw new IllegalArgumentException("Banco não encontrado");
            }
            if (!banco.getChave().equalsIgnoreCase(chave)) {
                throw new IllegalArgumentException("Chave incorrecta!");
            }

            return getContas(banco.getUrl());



        });
    }
    public Mono<TransacaoResposta> getContas(String url) {

        return getWebClient(url).get()
                .uri("/transacao")
                .header(HttpHeaders.AUTHORIZATION, Session.token)
                .retrieve()
                .bodyToMono(TransacaoResposta.class);
    }

    WebClient getWebClient(String url) {
        return  WebClient.builder()
                .baseUrl(url)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }







}

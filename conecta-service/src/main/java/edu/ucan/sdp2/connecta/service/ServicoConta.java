package edu.ucan.sdp2.connecta.service;


import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class ServicoConta {

    public static Mono<Boolean> checarConta(String host, String iban) {
        return WebClient.create(host.concat("/contas")).get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("iban", iban)
                        .build())
                .exchange()
                .flatMap(response -> Mono.just(response.statusCode().is2xxSuccessful()));
    }



}

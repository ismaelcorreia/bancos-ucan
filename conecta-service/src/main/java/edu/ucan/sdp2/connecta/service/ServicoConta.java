package edu.ucan.sdp2.connecta.service;


import edu.ucan.sdp2.conectacore.enums.StatusTransacao;
import edu.ucan.sdp2.conectacore.enums.TipoEvento;
import edu.ucan.sdp2.conectacore.listener.OuvinteAbstract;
import edu.ucan.sdp2.conectacore.models.TransacaoRequisicao;
import edu.ucan.sdp2.conectacore.service.ConectaEmissorService;
import edu.ucan.sdp2.conectacore.utils.BancoUtil;
import edu.ucan.sdp2.connecta.dto.Resposta;
import edu.ucan.sdp2.connecta.dto.conta.ContaResposta;
import edu.ucan.sdp2.connecta.dto.transacao.TransacaoStatusResposta;
import edu.ucan.sdp2.connecta.model.Conta;
import edu.ucan.sdp2.connecta.model.Transacao;
import edu.ucan.sdp2.connecta.repo.BancoRepo;
import edu.ucan.sdp2.connecta.repo.TransacaoRepo;
import edu.ucan.sdp2.connecta.utils.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ServicoConta {


    private final BancoRepo bancoRepo;
    public Mono<ContaResposta> allMine(
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
    public Mono<ContaResposta> getContas(String url) {

        return getWebClient(url).get()
                .uri("/contas")
                .header(HttpHeaders.AUTHORIZATION, Session.token)
                .retrieve()
                .bodyToMono(ContaResposta.class);
    }

    WebClient getWebClient(String url) {
        return  WebClient.builder()
                .baseUrl(url)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }







}

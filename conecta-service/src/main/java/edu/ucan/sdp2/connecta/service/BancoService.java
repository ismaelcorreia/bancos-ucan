package edu.ucan.sdp2.connecta.service;


import edu.ucan.sdp2.connecta.dto.banco.BancoCadastroRequisicao;
import edu.ucan.sdp2.connecta.dto.banco.BancoResposta;
import edu.ucan.sdp2.connecta.dto.transacao.TransacaoStatusResposta;
import edu.ucan.sdp2.connecta.mappers.BancoMapper;
import edu.ucan.sdp2.connecta.model.Transacao;
import edu.ucan.sdp2.connecta.repo.BancoRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BancoService {

    private final BancoRepo repo;
    private final BancoMapper mapper;
    public Mono<ResponseEntity<BancoResposta>> registar(BancoCadastroRequisicao requisicao) {
        if (!requisicao.validateChave()){
          return  Mono.error(new Throwable("O campo chave é obrigatório e deve conter 14 caracteres, incluindo letras, números e caracteres especiais."));
        }
        var banco = mapper.getBancoFromCadastroRequisicao(requisicao);
       banco.setId(UUID.randomUUID());
        banco.setCanal(banco.getCodigoIBAN().toLowerCase()+LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        return repo.save(banco).map( banco1 ->  ResponseEntity.ok(mapper.getBancoRespostaFromBanco(banco1)));
    }

    public Mono<ResponseEntity<List<BancoResposta>>> all() {
        return repo.
                findAll().
                collectList().map(bancos ->
                                ResponseEntity.ok(
                                        bancos.stream().map(mapper::getBancoRespostaMiniFromBanco).collect(Collectors.toList())
                                ));
    }


    public Mono<ResponseEntity<BancoResposta>> findDados(String chave, UUID id) {
        return repo.findById(id).map(
                        banco -> {
                            if (banco == null) {
                                throw new IllegalArgumentException("Banco não encontrado");
                            }
                            if (!banco.getChave().equals(chave.toUpperCase())) {
                                throw new IllegalArgumentException("Chave incorrecta!");
                            }
                            return ResponseEntity.ok(mapper.getBancoRespostaFromBanco(banco));
                        }
                );

    }


}

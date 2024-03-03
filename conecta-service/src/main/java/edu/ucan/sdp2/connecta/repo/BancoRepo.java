package edu.ucan.sdp2.connecta.repo;

import edu.ucan.sdp2.connecta.model.Banco;
import edu.ucan.sdp2.connecta.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface BancoRepo extends ReactiveMongoRepository<Banco, UUID> {

    Mono<Banco> findByChaveAndId(String chave, UUID id);
    Mono<Banco> findByCanal(String canal);
    Mono<Banco> findByCodigoIBAN(String codigoIBAN);
}
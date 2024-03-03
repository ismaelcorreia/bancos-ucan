package edu.ucan.sdp2.connecta.repo;

import edu.ucan.sdp2.connecta.model.Transacao;
import edu.ucan.sdp2.connecta.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import java.util.UUID;

public interface TransacaoRepo extends ReactiveMongoRepository<Transacao, UUID> {
}
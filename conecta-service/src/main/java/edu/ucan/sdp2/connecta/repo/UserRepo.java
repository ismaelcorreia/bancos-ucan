package edu.ucan.sdp2.connecta.repo;

import edu.ucan.sdp2.connecta.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface UserRepo extends ReactiveMongoRepository<User, UUID> {
}
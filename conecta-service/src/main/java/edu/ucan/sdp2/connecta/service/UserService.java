package edu.ucan.sdp2.connecta.service;

import edu.ucan.sdp2.connecta.model.User;
import edu.ucan.sdp2.connecta.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    private final UserRepo userRepository;

    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Mono<User> getUserById(@PathVariable String id) {
        return userRepository.findById(UUID.fromString(id));
    }


    public Mono<User> createUser(@RequestBody User user) {
        return userRepository.save(user);
    }


    public Mono<User> updateUser(@PathVariable String id, @RequestBody User user) {
        return userRepository.findById(UUID.fromString(id))
                .flatMap(existingUser -> {

                    existingUser.setName(user.getName());

                    return userRepository.save(existingUser);
                });
    }

    public Mono<Void> deleteUser(@PathVariable String id) {
        return userRepository.deleteById(UUID.fromString(id));
    }
}

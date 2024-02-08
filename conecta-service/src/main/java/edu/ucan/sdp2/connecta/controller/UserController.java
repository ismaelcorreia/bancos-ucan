package edu.ucan.sdp2.connecta.controller;

import edu.ucan.sdp2.connecta.model.User;
import edu.ucan.sdp2.connecta.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserRepo userRepository;
//
//    @Autowired
//    public UserController(UserRepo userRepository) {
//        this.userRepository = userRepository;
//    }

    @GetMapping
    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<User> getUserById(@PathVariable String id) {
        return userRepository.findById(id);
    }

    @PostMapping
    public Mono<User> createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @PutMapping("/{id}")
    public Mono<User> updateUser(@PathVariable String id, @RequestBody User user) {
        return userRepository.findById(id)
                .flatMap(existingUser -> {
                    existingUser.setName(user.getName());
                    existingUser.setAge(user.getAge());
                    return userRepository.save(existingUser);
                });
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteUser(@PathVariable String id) {
        return userRepository.deleteById(id);
    }
}
package edu.ucan.sdp2.connecta.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document("users")
@Data
public class User {
    @Id
    private UUID id;
    private String name;
    private int age;

    // getters, setters, construtores
}
package edu.ucan.sdp2.connecta.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.UUID;

@Document("users")
@Data
public class User {
    @MongoId
    private UUID id;
    private String name;
    private String iban;
}
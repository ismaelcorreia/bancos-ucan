package edu.ucan.sdp2.connecta.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.UUID;

@Data
@Builder
public class Cliente {
    private String nome;
    private String iban;
    private String numeroTelefone;
}
package edu.ucan.sdp2.connecta.model;

import jakarta.annotation.Generated;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.UUID;

@Document("banco")
@Data
public class Banco {

    @Id
    private UUID id;
    private String nome;
    private String sigla;
    private String codigoIBAN;
    private String canal;
    private String chave;
    private String url;
}
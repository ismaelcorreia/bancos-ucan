package edu.ucan.sdp2.connecta.model;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@ToString
@Document("transacao")
@Data
public class Transacao implements Serializable {
    @MongoId
    private UUID id;
    private String numero;
    private String bancoId;
    private String status;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataExecucao;
    private LocalDateTime dataUltimaModificacao;
    private TransacaoDetalhe detalhes;
}

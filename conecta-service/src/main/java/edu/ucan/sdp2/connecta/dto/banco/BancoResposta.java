package edu.ucan.sdp2.connecta.dto.banco;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class BancoResposta {
    private UUID id;
    private String nome;
    private String sigla;
    private String codigoIBAN;
    private String canal;
}

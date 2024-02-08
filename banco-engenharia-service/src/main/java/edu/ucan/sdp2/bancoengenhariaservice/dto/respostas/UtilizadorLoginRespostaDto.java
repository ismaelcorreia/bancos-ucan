package edu.ucan.sdp2.bancoengenhariaservice.dto.respostas;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.With;

@Getter
@Setter
@With
@Builder
public class UtilizadorLoginRespostaDto {
    private String token;
    private UtilizadorRespostaDto utilizador;
}

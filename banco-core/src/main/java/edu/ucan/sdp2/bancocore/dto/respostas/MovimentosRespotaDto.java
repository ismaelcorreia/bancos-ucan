package edu.ucan.sdp2.bancocore.dto.respostas;

import edu.ucan.sdp2.bancocore.enums.TipoMovimento;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class MovimentosRespotaDto {

    private UUID id;
    private TipoMovimento tipoMovimento;
    private Double valor;
    private Double valorAnterior;
    private Double valorPosterir;
    private LocalDateTime data;
}

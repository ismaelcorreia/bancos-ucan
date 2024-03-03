package edu.ucan.sdp2.bancocore.dto.respostas;


import edu.ucan.sdp2.bancocore.enums.StatusTransacao;
import edu.ucan.sdp2.bancocore.enums.TipoOperacao;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TransacaoRespotaDto {

    private UUID id;
    private TipoOperacao tipoTransacao;
    private String ibanDestino;
    private Double valor;
    private LocalDateTime data;
}

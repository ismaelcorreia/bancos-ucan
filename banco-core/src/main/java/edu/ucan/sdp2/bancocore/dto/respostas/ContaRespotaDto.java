package edu.ucan.sdp2.bancocore.dto.respostas;

import edu.ucan.sdp2.bancocore.enums.TipoMovimento;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ContaRespotaDto {

    private UUID id;
    private String numero;
    private String iban;
    private Double saldoDisponivel;
    private Double saldoContabilistico;
    private LocalDateTime ultimaActualizacao;

}

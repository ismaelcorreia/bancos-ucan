package edu.ucan.sdp2.connecta.dto.transacao;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
public class TransacaoStatusResposta implements Serializable {
    private String transacaoId;
    private String numero;
    private String status;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataExecucao;
    private LocalDateTime dataUltimaModificacao;
}

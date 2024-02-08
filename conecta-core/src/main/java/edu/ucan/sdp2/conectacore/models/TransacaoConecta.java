package edu.ucan.sdp2.conectacore.models;

import com.google.gson.Gson;
import edu.ucan.sdp2.conectacore.enums.Moeda;
import edu.ucan.sdp2.conectacore.enums.StatusTransacao;
import edu.ucan.sdp2.conectacore.enums.TipoOperacao;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Data

public class TransacaoConecta implements Serializable {
    private UUID id;
    private String origem;
    private String destino;
    private BigDecimal montante;
    private TipoOperacao tipoOperacao;
    private Moeda moeda;
    private Date dataHoraTransacao;
    private StatusTransacao status;

    public String toJson() {
        return new Gson().toJson(this);
    }
}

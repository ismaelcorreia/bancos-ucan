package edu.ucan.sdp2.connecta.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Conta {
    private String id;
    private String numero;
    private String iban;
    private Double saldoDisponivel;
    private Double saldoContabilistico;
    private String ultimaActualizacao;

}

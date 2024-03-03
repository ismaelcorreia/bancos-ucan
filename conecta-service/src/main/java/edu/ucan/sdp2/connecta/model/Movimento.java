package edu.ucan.sdp2.connecta.model;

import lombok.Data;

@Data
public class Movimento {

    private String id;
    private String tipoMovimento;
    private double valor;
    private double valorAnterior;
    private double valorPosterior;
    private String data;
}

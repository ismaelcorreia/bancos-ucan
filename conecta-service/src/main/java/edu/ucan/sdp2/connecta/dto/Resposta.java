package edu.ucan.sdp2.connecta.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Resposta<T> {

    private String mensagem;
    private String status;
    private int codigo;
    private List<T> dados;
    private String timestamp;
}

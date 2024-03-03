package edu.ucan.sdp2.bancocore.utils;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
@Getter
@Setter
public class Variables {

    @Value("${banco.identificador.chave}")
    private String chave;
    @Value("${banco.identificador.id}")
    private String id;
    @Value("${banco.identificador.sigla}")
    private String sigla;
    @Value("${banco.identificador.iban}")
    private String iban;
    @Value("${conecta.url.transacao.requisicao}")
    private String conecatUrlRequisicao;



}

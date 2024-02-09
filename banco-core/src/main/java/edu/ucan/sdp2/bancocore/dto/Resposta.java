package edu.ucan.sdp2.bancocore.dto;


import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;


@Getter
public class Resposta<T> {
    private String mensagem;
    private HttpStatus status;
    private Integer codigo;
    private T dados;
    private final LocalDateTime timestamp = LocalDateTime.now();

    public Resposta(String mensagem, T dados){
        this.mensagem = mensagem;
        this.dados = dados;
    }
    public Resposta(T dados){
        this("No message available!", dados);
    }

    public ResponseEntity<Resposta> sucesso(){
        this.status = HttpStatus.OK;
        this.codigo = HttpStatus.OK.value();
        return  ResponseEntity.status(HttpStatus.OK).body(this);
    }
    public ResponseEntity<Resposta>  error(){
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
        this.codigo = HttpStatus.INTERNAL_SERVER_ERROR.value();
        return  ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR ).body(this);
    }
    public ResponseEntity<Resposta> naoEncontrado(){
        this.status = HttpStatus.NOT_FOUND;
        this.codigo = HttpStatus.NOT_FOUND.value();
        return  ResponseEntity.status( HttpStatus.NOT_FOUND ).body(this);
    }
    public ResponseEntity<Resposta> proibido(){
        this.status = HttpStatus.FORBIDDEN;
        this.codigo = HttpStatus.FORBIDDEN.value();
        return  ResponseEntity.status( HttpStatus.FORBIDDEN ).body(this);
    }

    public ResponseEntity<Resposta> recusado(){
        this.status = HttpStatus.NOT_ACCEPTABLE;
        this.codigo = HttpStatus.NOT_ACCEPTABLE.value();
        return  ResponseEntity.status( HttpStatus.NOT_ACCEPTABLE ).body(this);
    }
    public ResponseEntity<Resposta> criado(){
        this.status = HttpStatus.CREATED;
        this.codigo = HttpStatus.CREATED.value();
        return  ResponseEntity.status(HttpStatus.CREATED).body(this);
    }

}

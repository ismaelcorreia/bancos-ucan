package edu.ucan.sdp2.bancocore.entities;


import edu.ucan.sdp2.bancocore.commons.GenericValor;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "enderecos")
public class Endereco extends GenericValor {
    @ManyToOne(targetEntity = Endereco.class, fetch = FetchType.LAZY)
    private Endereco enderecoPai;
    public Endereco(UUID id){
        super(id);
    }
    public Endereco(String nome){
        super(nome);
    }
    public Endereco(String nome, String nomePai){
        super(nome);
        enderecoPai = new Endereco(nomePai);
    }

    public Endereco(String nome, UUID idPai){
        super(nome);
        enderecoPai = new Endereco(idPai);
    }

    public Endereco(String nome, Endereco enderecoPai){
        super(nome);
        this.enderecoPai = enderecoPai;
    }
}

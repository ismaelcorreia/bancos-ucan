package edu.ucan.sdp2.conectacore.models;

import com.google.gson.Gson;
import lombok.Data;

import java.io.Serializable;

@Data
public class TransacaoConecta implements Serializable {

    private String topico;
    private TransacaoConectaDetalhes detalhes;
    public String toJson() {
        return new Gson().toJson(this);
    }

    public static TransacaoConecta fromJson(String json) {
        return new Gson().fromJson(json, TransacaoConecta.class);
    }

    public boolean isValido() {
        if (this.topico == null || this.topico.isBlank() ) {
            return false;
        }
        return this.detalhes != null && this.detalhes.isValido();
    }
}

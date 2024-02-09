package edu.ucan.sdp2.conectacore.models;

import com.google.gson.Gson;
import lombok.Data;

import java.io.Serializable;
@Data
public class TransacaoConectaDetalhes  implements Serializable {

    private String iban;
    private double valor;
    private String tipoMovimento;


    public boolean isValido() {
        if (this.iban == null || this.iban.isBlank() ) {
            return false;
        } else  if (this.tipoMovimento == null || this.tipoMovimento.isBlank() ) {
            return false;
        }
        return !(this.valor <= 0);
    }
    public String toJson() {
        return new Gson().toJson(this);
    }
    public static TransacaoConectaDetalhes fromJson(String json) {
        return new Gson().fromJson(json, TransacaoConectaDetalhes.class);
    }

}

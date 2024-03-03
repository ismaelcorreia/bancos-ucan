package edu.ucan.sdp2.conectacore.models;

import com.google.gson.Gson;
import edu.ucan.sdp2.conectacore.enums.TipoOperacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransacaoConectaDetalhes  implements Serializable {

    private String ibanOrigem;
    private String ibanDestino;
    private Double valor;
    private TipoOperacao tipoOperacao;


    public boolean isValido() {
        if (this.ibanOrigem == null || this.ibanOrigem.isBlank() ) {
            return false;
        } else if (this.ibanDestino == null || this.ibanDestino.isBlank() ) {
            return false;
        } else if (this.tipoOperacao == null) {
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

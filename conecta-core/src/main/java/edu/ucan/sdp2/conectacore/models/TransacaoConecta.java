package edu.ucan.sdp2.conectacore.models;

import com.google.gson.Gson;
import edu.ucan.sdp2.conectacore.enums.TipoEvento;
import edu.ucan.sdp2.conectacore.enums.TipoOperacao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransacaoConecta implements Serializable {

    private String numero;
    private String topico;
    private TipoEvento tipoEvento;
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

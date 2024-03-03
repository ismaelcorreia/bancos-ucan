package edu.ucan.sdp2.conectacore.models;

import com.google.gson.Gson;
import edu.ucan.sdp2.conectacore.utils.EncriptadorTransacaoConecta;
import lombok.Data;
import lombok.SneakyThrows;

import java.io.Serializable;

@Data
public class TransacaoConectaEncriptado implements Serializable {

    private String numeroTransacao;
    private String topico;
    private String dados;
    public String toJson() {
        return new Gson().toJson(this);
    }

    public static TransacaoConectaEncriptado fromJson(String json) {
        return new Gson().fromJson(json, TransacaoConectaEncriptado.class);
    }

    @SneakyThrows
    public static TransacaoConectaEncriptado fromTransacao(TransacaoConecta transacaoConecta, String chave) {
        TransacaoConectaEncriptado transacaoConectaEncriptado = new TransacaoConectaEncriptado();
        transacaoConectaEncriptado.setTopico(transacaoConecta.getTopico());
        transacaoConectaEncriptado.setNumeroTransacao(transacaoConecta.getNumero());
        transacaoConectaEncriptado.setDados(EncriptadorTransacaoConecta.encriptar(chave, transacaoConecta.getDetalhes().toJson()));
        return transacaoConectaEncriptado;
    }


    @SneakyThrows
    public static TransacaoConecta  toTransacao(TransacaoConectaEncriptado transacaoConectaEncriptado, String chave) {
        TransacaoConecta transacaoConecta = new TransacaoConecta();
        transacaoConecta.setNumero(transacaoConectaEncriptado.getNumeroTransacao());
        transacaoConecta.setTopico(transacaoConectaEncriptado.getTopico());
        var json = EncriptadorTransacaoConecta.desencriptar(chave, transacaoConectaEncriptado.getDados());
        transacaoConecta.setDetalhes(TransacaoConectaDetalhes.fromJson(json));
        return transacaoConecta;
    }
    public boolean isValido() {
        if (this.topico == null || this.topico.isBlank() ) {
            return false;
        }
        return this.dados != null;
    }
}

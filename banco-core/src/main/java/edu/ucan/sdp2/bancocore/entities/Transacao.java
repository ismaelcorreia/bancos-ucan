package edu.ucan.sdp2.bancocore.entities;


import edu.ucan.sdp2.bancocore.commons.GenericId;
import edu.ucan.sdp2.bancocore.enums.StatusTransacao;
import edu.ucan.sdp2.bancocore.enums.TipoOperacao;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "transacoes")
public class Transacao extends GenericId {


    private String contaDestino;
    @ManyToOne(fetch = FetchType.EAGER)
    private Movimento movimento;
    private TipoOperacao tipoOperacao;
    @ManyToOne(fetch = FetchType.EAGER)
    private Utilizador operador;
    @Enumerated
    private StatusTransacao statusTransacao;

    @Override
    public String toString() {
        return "Transacao{" +
                "contaDestino='" + contaDestino + '\'' +
                ", tipoOperacao=" + tipoOperacao +
                ", operador=" + operador +
                ", statusTransacao=" + statusTransacao +
                '}';
    }
}

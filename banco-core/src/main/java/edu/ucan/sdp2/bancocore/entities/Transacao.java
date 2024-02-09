package edu.ucan.sdp2.bancocore.entities;


import edu.ucan.sdp2.bancocore.commons.GenericId;
import edu.ucan.sdp2.bancocore.enums.StatusTransacao;
import edu.ucan.sdp2.conectacore.enums.TipoOperacao;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
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
    @ManyToOne
    private Movimento movimento;
    private TipoOperacao tipoOperacao;
    @ManyToOne
    private Utilizador operador;
    @Enumerated
    private StatusTransacao statusTransacao;


}

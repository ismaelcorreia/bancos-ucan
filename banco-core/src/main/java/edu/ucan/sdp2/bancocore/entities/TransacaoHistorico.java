package edu.ucan.sdp2.bancocore.entities;


import edu.ucan.sdp2.bancocore.commons.GenericId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "transacao_historicos")
public class TransacaoHistorico extends GenericId {

    @ManyToOne
    private Transacao contaOrigem;
//    private StatusTransacao status;


}

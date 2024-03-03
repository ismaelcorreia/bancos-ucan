package edu.ucan.sdp2.bancocore.entities;


import edu.ucan.sdp2.bancocore.commons.GenericId;
import edu.ucan.sdp2.bancocore.enums.TipoMovimento;
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
@Entity(name = "movimentos")
public class Movimento extends GenericId {

    @ManyToOne(fetch = FetchType.EAGER)
    private ContaBancaria conta;
    @Enumerated
    private TipoMovimento tipoMovimento;
    private Double valorMovimento;
    private Double saldoAnterior;
    private Double saldoDepois;

    @Override
    public String toString() {
        return "Movimento{" +
                "conta=" + conta +
                ", tipoMovimento=" + tipoMovimento +
                ", valorMovimento=" + valorMovimento +
                ", saldoAnterior=" + saldoAnterior +
                ", saldoDepois=" + saldoDepois +
                '}';
    }
}

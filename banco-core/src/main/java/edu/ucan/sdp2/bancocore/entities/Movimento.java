package edu.ucan.sdp2.bancocore.entities;


import edu.ucan.sdp2.bancocore.commons.GenericId;
import edu.ucan.sdp2.bancocore.enums.TipoMovimento;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "movimentos")
public class Movimento extends GenericId {

    @ManyToOne
    private ContaBancaria conta;
    @Enumerated
    private TipoMovimento tipoMovimento;
    private Double valorMovimento;
    private Double saldoAnterior;
    private Double saldoDepois;
}

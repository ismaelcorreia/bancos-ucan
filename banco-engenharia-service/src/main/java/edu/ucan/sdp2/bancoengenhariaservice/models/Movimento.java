package edu.ucan.sdp2.bancoengenhariaservice.models;

import edu.ucan.sdp2.bancoengenhariaservice.enums.TipoMovimento;
import edu.ucan.sdp2.bancoengenhariaservice.models.generics.GenericId;
import edu.ucan.sdp2.bancoengenhariaservice.models.generics.GenericValue;
import edu.ucan.sdp2.conectacore.enums.Moeda;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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

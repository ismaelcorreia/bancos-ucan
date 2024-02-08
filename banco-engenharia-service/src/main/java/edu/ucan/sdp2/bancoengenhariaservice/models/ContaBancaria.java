package edu.ucan.sdp2.bancoengenhariaservice.models;

import edu.ucan.sdp2.bancoengenhariaservice.models.generics.GenericId;
import edu.ucan.sdp2.bancoengenhariaservice.models.generics.GenericValue;
import edu.ucan.sdp2.conectacore.enums.Moeda;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "contas_bancaria")
public class ContaBancaria extends GenericId {

    @Column(unique = true, updatable = false, nullable = false)
    private String numeroConta;
    @Column(unique = true, updatable = false, nullable = false)
    private String ibanConta;
    @ManyToMany
    private List<Utilizador> representantes;
    private Double saldoDisponivel;
    private Double saldoContabilistico;
    @ManyToMany
    private List<ContaBancaria> subContasBancaria;
    private Moeda moeda;
    public ContaBancaria(UUID id) {
        super(id);
    }


}

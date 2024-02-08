package edu.ucan.sdp2.bancoengenhariaservice.models;

import edu.ucan.sdp2.bancoengenhariaservice.models.generics.GenericValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "contas_bancaria")
public class ContaBancaria extends GenericValue {
    @ManyToMany
    private List<Utilizador> representantes;
    private Double saldoDisponivel;
    private Double saldoContabilistico;
}

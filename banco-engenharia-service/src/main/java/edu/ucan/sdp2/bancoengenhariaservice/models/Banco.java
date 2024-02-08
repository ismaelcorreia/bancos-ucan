package edu.ucan.sdp2.bancoengenhariaservice.models;

import edu.ucan.sdp2.bancoengenhariaservice.models.generics.GenericValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "bancos")
public class Banco extends GenericValue {

    @ManyToOne
    private Endereco endereco;
}

package edu.ucan.sdp2.bancoengenhariaservice.models;

import edu.ucan.sdp2.bancoengenhariaservice.models.generics.GenericId;
import edu.ucan.sdp2.bancoengenhariaservice.models.generics.GenericValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity(name = "enderecos")
public class Endereco extends GenericValue {
    @ManyToOne
    private Endereco enderecoPai;
}

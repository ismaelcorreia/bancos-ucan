package edu.ucan.sdp2.bancocore.entities;

import edu.ucan.sdp2.bancocore.commons.GenericValor;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "bancos")
public class Banco extends GenericValor {

    @ManyToOne
    private Endereco endereco;
}

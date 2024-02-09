package edu.ucan.sdp2.bancocore.entities;


import edu.ucan.sdp2.bancocore.commons.GenericId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="telefone_verificacao")
public class TelefoneVerificacao extends GenericId {
    private String telefone;
    private String codigo;
}

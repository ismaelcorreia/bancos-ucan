package edu.ucan.sdp2.bancocore.commons;


import edu.ucan.sdp2.bancocore.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor
@AllArgsConstructor
public abstract class GenericValor extends GenericId{

    @Column(nullable = false)
    private String nome;
    private String descricao;

    public GenericValor(UUID id, Status status) {
        super(id, status);
    }
    public GenericValor(String nome) {
        this.nome = nome;
    }

    public GenericValor(UUID id) {
        super(id);
    }
}
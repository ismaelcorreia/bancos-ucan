package edu.ucan.sdp2.bancoengenhariaservice.models.generics;


import edu.ucan.sdp2.bancoengenhariaservice.enums.Status;
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
public abstract class GenericValue extends GenericId{

    @Column(nullable = false)
    private String nome;
    private String descricao;

    public GenericValue(UUID id, Status status) {
        super(id, status);
    }
    public GenericValue(String nome) {
        this.nome = nome;
    }

    public GenericValue(UUID id) {
        super(id);
    }
}
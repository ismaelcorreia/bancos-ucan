package edu.ucan.sdp2.bancocore.commons;

import edu.ucan.sdp2.bancocore.entities.Endereco;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class GenericPessoa extends GenericId {

    @Column(nullable = false)
    private String nomeCompleto;
    private String fotografia;
    @Column(unique = true)
    private String numeroBilhete;
    private LocalDate dataNascimento;
    @ManyToOne
    private Endereco enderecoActual;
    @ManyToOne
    private Endereco enderecoNascimento;

}
package edu.ucan.sdp2.bancocore.commons;


import edu.ucan.sdp2.bancocore.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor
@AllArgsConstructor
public abstract class GenericId {

    @Id
    private UUID id;
    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataActualizacao;

    public GenericId(UUID id, Status status) {
        this.id = id;
        this.status = status;
    }

    public GenericId(UUID id) {
        this.id = id;
    }
    @PrePersist
    private void criacao(){
        this.id = UUID.randomUUID();
        dataCriacao = LocalDateTime.now();
        if (this.status == null){
            this.status = Status.ACTIVE;
        }
    }
    @PreUpdate
    private void actualizacao(){
        dataActualizacao = LocalDateTime.now();
    }

}
package edu.ucan.sdp2.bancocore.repositories;


import edu.ucan.sdp2.bancocore.entities.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EnderecoRepository extends JpaRepository<Endereco, UUID> {

    Endereco getFirstByNome(String nome);
}

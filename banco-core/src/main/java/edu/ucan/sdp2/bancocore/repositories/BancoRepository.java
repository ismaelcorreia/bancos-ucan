package edu.ucan.sdp2.bancocore.repositories;


import edu.ucan.sdp2.bancocore.entities.Banco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BancoRepository extends JpaRepository<Banco, UUID> {

}

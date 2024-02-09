package edu.ucan.sdp2.bancocore.repositories;


import edu.ucan.sdp2.bancocore.entities.TransacaoHistorico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransacaoHistoricoRepository extends JpaRepository<TransacaoHistorico, UUID> {

}

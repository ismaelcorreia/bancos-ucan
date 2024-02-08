package edu.ucan.sdp2.bancoengenhariaservice.repositories;


import edu.ucan.sdp2.bancoengenhariaservice.models.Transacao;
import edu.ucan.sdp2.bancoengenhariaservice.models.TransacaoHistorico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransacaoHistoricoRepository extends JpaRepository<TransacaoHistorico, UUID> {

}

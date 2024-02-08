package edu.ucan.sdp2.bancoengenhariaservice.repositories;


import edu.ucan.sdp2.bancoengenhariaservice.models.Banco;
import edu.ucan.sdp2.bancoengenhariaservice.models.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransacaoRepository extends JpaRepository<Transacao, UUID> {

}

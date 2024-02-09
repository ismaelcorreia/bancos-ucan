package edu.ucan.sdp2.bancocore.repositories;


import edu.ucan.sdp2.bancocore.entities.Transacao;
import edu.ucan.sdp2.bancocore.entities.Utilizador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TransacaoRepository extends JpaRepository<Transacao, UUID> {
    List<Transacao> findAllByOperadorOrderByDataCriacaoDesc(Utilizador operador);
}

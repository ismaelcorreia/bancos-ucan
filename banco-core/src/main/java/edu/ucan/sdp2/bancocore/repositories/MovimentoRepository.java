package edu.ucan.sdp2.bancocore.repositories;


import edu.ucan.sdp2.bancocore.entities.ContaBancaria;
import edu.ucan.sdp2.bancocore.entities.Movimento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MovimentoRepository extends JpaRepository<Movimento, UUID> {

    List<Movimento> findAllByConta(ContaBancaria contaBancaria);
    List<Movimento> findAllByContaOrderByDataCriacaoDesc(ContaBancaria contaBancaria);
}

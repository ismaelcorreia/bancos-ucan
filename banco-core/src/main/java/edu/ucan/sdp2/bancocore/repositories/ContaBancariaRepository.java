package edu.ucan.sdp2.bancocore.repositories;


import edu.ucan.sdp2.bancocore.entities.ContaBancaria;
import edu.ucan.sdp2.bancocore.entities.Utilizador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ContaBancariaRepository extends JpaRepository<ContaBancaria, UUID> {


    ContaBancaria findFirstByIbanConta(String iban);
    ContaBancaria findFirstByNumeroConta(String numeroConta);
    ContaBancaria findFirstByNumeroContaOrIbanConta(String numeroConta, String iban);
    List<ContaBancaria> findByRepresentantesIn(List<Utilizador> representantes);



}

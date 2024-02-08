package edu.ucan.sdp2.bancoengenhariaservice.repositories;


import edu.ucan.sdp2.bancoengenhariaservice.models.ContaBancaria;
import edu.ucan.sdp2.bancoengenhariaservice.models.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContaBancariaRepository extends JpaRepository<ContaBancaria, UUID> {

}

package edu.ucan.sdp2.bancocore.repositories;



import edu.ucan.sdp2.bancocore.entities.Utilizador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UtilizadorRepository extends JpaRepository<Utilizador, UUID> {
    Utilizador findByUsername(String username);
    Utilizador findFirstByEmail(String email);
    Utilizador findByEmailOrUsernameOrTelefone(String email, String username, String telefone);
    Utilizador findByNomeCompleto(String fullName);
    Utilizador findFirstByNomeCompletoContainingIgnoreCase(String nomeCompleto);
}

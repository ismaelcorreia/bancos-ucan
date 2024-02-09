package edu.ucan.sdp2.bancocore.repositories;


import edu.ucan.sdp2.bancocore.enums.Status;
import edu.ucan.sdp2.bancocore.entities.TelefoneVerificacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TelefoneVerificacaoRepository extends JpaRepository<TelefoneVerificacao, UUID> {
    public TelefoneVerificacao getTelefoneVerificaoByTelefoneAndCodigo(String telefone, String codigo);
    public TelefoneVerificacao getFirstByTelefoneAndStatus(String phoneNumber, Status status);

}

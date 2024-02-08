package edu.ucan.sdp2.bancoengenhariaservice.repositories;


import edu.ucan.sdp2.bancoengenhariaservice.enums.Status;
import edu.ucan.sdp2.bancoengenhariaservice.models.TelefoneVerificacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TelefoneVerificacaoRepository extends JpaRepository<TelefoneVerificacao, UUID> {
    public TelefoneVerificacao getTelefoneVerificaoByTelefoneAndCodigo(String telefone, String codigo);
    public TelefoneVerificacao getFirstByTelefoneAndStatus(String phoneNumber, Status status);

}

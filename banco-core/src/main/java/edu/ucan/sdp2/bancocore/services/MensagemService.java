package edu.ucan.sdp2.bancocore.services;


import edu.ucan.sdp2.bancocore.repositories.TelefoneVerificacaoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class MensagemService {


    private final TelefoneVerificacaoRepository telefoneVerificacaoRepository;

}

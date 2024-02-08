package edu.ucan.sdp2.bancoengenhariaservice.services;

import edu.ucan.sdp2.bancoengenhariaservice.configuration.messages.ISMSServico;
import edu.ucan.sdp2.bancoengenhariaservice.configuration.messages.SMSModelo;
import edu.ucan.sdp2.bancoengenhariaservice.dto.Resposta;
import edu.ucan.sdp2.bancoengenhariaservice.enums.Status;
import edu.ucan.sdp2.bancoengenhariaservice.models.TelefoneVerificacao;
import edu.ucan.sdp2.bancoengenhariaservice.repositories.TelefoneVerificacaoRepository;
import edu.ucan.sdp2.bancoengenhariaservice.utils.ManipuladorTextoUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class MensagemService {


    private final TelefoneVerificacaoRepository telefoneVerificacaoRepository;

}

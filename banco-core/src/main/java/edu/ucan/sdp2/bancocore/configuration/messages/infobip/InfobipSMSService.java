package edu.ucan.sdp2.bancocore.configuration.messages.infobip;



import edu.ucan.sdp2.bancocore.configuration.messages.ISMSServico;
import edu.ucan.sdp2.bancocore.configuration.messages.SMSModelo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InfobipSMSService implements ISMSServico {

    private final InfoBipConfiguration infoBipConfiguration;

    @Override
    public void enviarMensagem(SMSModelo senderModel) {
        infoBipConfiguration.prepareAndSend(senderModel.getTelefone(), senderModel.getMensagem());

    }
}

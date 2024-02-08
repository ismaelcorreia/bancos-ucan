package edu.ucan.sdp2.bancoengenhariaservice;


import edu.ucan.sdp2.bancoengenhariaservice.configuration.messages.SMSModelo;
import edu.ucan.sdp2.bancoengenhariaservice.configuration.messages.infobip.InfobipSMSService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class InfoBipSMSServiceTest {
    private final InfobipSMSService infobipSMSService;

    @Autowired
    public InfoBipSMSServiceTest(InfobipSMSService infobipSMSService) {
        this.infobipSMSService = infobipSMSService;
    }

    @Test
    void enviarMensageRita() {
        SMSModelo modelo = new SMSModelo("244949034116","Estou a sair de casa!");
        infobipSMSService.enviarMensagem(modelo);
    }
}

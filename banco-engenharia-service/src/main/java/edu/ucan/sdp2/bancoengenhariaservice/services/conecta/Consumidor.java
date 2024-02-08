package edu.ucan.sdp2.bancoengenhariaservice.services.conecta;

import edu.ucan.sdp2.conectacore.listener.OuvinteAbstract;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;



@Slf4j
@Component
public class Consumidor extends OuvinteAbstract
{


    @SneakyThrows
    @Override
    public void escuta(String json) {
        log.info("Banco-Engenharia consumer: {}", json);
    }
}

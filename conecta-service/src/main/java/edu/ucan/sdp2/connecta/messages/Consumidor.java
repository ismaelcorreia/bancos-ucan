package edu.ucan.sdp2.connecta.messages;

import edu.ucan.sdp2.conectacore.listener.OuvinteAbstract;
import edu.ucan.sdp2.conectacore.utils.JsonUtil;
import lombok.SneakyThrows;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;



@Slf4j
@Component
public class Consumidor extends OuvinteAbstract {


    @SneakyThrows
    @Override
    public void escuta(String json) {
        log.info("Conecta consumer: {}", json);

    }
}

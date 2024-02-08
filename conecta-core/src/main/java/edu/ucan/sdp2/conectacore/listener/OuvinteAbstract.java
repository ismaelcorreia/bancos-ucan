package edu.ucan.sdp2.conectacore.listener;
import org.springframework.kafka.annotation.KafkaListener;

public abstract class OuvinteAbstract {


    public abstract void escuta(String json);

    // Ouvintes de Transferências
    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.template.default-topic}"
    )
    protected void escutaTransferenciaSucesso(String json){
        escuta(json);
    }


}

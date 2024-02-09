package edu.ucan.sdp2.conectacore.listener;
import org.springframework.kafka.annotation.KafkaListener;

public abstract class OuvinteAbstract {


    public abstract void escuta(String json);
    public abstract void falha(String json);

    // Ouvintes de Transferências
    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.template.default-topic}"
    )
    protected void ouvintePadrao(String json){
        escuta(json);
    }

    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "${topic-fail}"
    )
    protected void ouvinteFalha(String json){
        falha(json);
    }


}

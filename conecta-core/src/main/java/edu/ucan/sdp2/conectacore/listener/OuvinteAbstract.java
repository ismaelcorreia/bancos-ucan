package edu.ucan.sdp2.conectacore.listener;
import edu.ucan.sdp2.conectacore.enums.TipoEvento;
import edu.ucan.sdp2.conectacore.enums.TipoEvento.*;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.kafka.annotation.KafkaListener;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
public abstract class OuvinteAbstract {


    public abstract void escuta(Map<TipoEvento, String> data);


    // Ouvintes de Transferências
    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.template.default-topic}"
    )
    protected void ouvintePadrao(ConsumerRecord<String, String> record){
        var message = new HashMap<TipoEvento, String>();
        var tipoEvento = TipoEvento.valueOf(record.key());
        message.put(tipoEvento, record.value());
        escuta(message);
        selecionarEvento(tipoEvento, record.value());
    }

    void selecionarEvento(TipoEvento evento, String data) {

                switch (evento) {
                    case REQUISICAO_TRANSFERENCIA -> {
                        requisicaoTransferencia(data);
                    }
                    case REQUISICAO_CREDITO -> {
                        requisicaoCredito(data);
                    }
                    case APROVACAO_CREDITO -> {
                        aprovacaoCredito(data);
                    }
                    case REALIZACAO_CREDITO -> {
                        realizacaoCredito(data);
                    }
                    case ROLLBACK_TRANSFERENCIA -> {
                        rollbackTransferencia(data);
                    }

                    case TRANSFERENCIA_REALIZADA -> {
                        transferenciaRealizada(data);
                    }



                }
    }
    public abstract void requisicaoTransferencia(String data);
    public abstract void requisicaoCredito(String data);
    public abstract void aprovacaoCredito(String data);
    public abstract void realizacaoCredito(String data);
    public abstract void rollbackTransferencia(String data);

    public abstract void transferenciaRealizada(String data);




}

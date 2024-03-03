package edu.ucan.sdp2.connecta.messages;


import edu.ucan.sdp2.conectacore.models.TransacaoConecta;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Producer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public Producer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void enviarTransacao(TransacaoConecta transacao, String topico) {
        try {
            log.info("Enviando dados ao topico {} com dados {}", topico, transacao.toJson());
            kafkaTemplate.send(topico, transacao.getTipoEvento().name(), transacao.toJson());
        } catch (Exception e) {
            log.error("Erro ao enviar dados ao topico {} com dados {}", topico, transacao, e);
        }
    }
}

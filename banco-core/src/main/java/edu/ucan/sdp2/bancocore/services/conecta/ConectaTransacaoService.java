package edu.ucan.sdp2.bancocore.services.conecta;


import edu.ucan.sdp2.conectacore.models.TransacaoConecta;
import edu.ucan.sdp2.conectacore.models.TransacaoRequisicao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ConectaTransacaoService {



    @Value("${conecta.url.transacao.requisicao}")
    private String conecatUrlRequisicao;

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public ConectaTransacaoService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void enviarEvento(TransacaoConecta transacao, String topico) {
        try {
            log.info("Enviando dados ao topico {} com dados {}", topico, transacao.toJson());
            kafkaTemplate.send(topico, transacao.getTipoEvento().name(), transacao.toJson());
        } catch (Exception e) {
            log.error("Erro ao enviar dados ao topico {} com dados {}", topico, transacao, e);
        }
    }


    public void enviarTransacao(TransacaoRequisicao requisicao) {
//        var transacaoRequest =

    }
}

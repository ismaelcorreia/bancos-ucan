package edu.ucan.sdp2.conectacore.service;


import edu.ucan.sdp2.conectacore.models.TransacaoConecta;
import edu.ucan.sdp2.conectacore.models.TransacaoConectaDetalhes;
import edu.ucan.sdp2.conectacore.models.TransacaoConectaEncriptado;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ConectaEmissorService {


    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public ConectaEmissorService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public boolean enviarMovimento(TransacaoConecta transacaoConecta, String chave, String topicoDestino) {
        try {
            log.info("Enviando debito para o topico {} com dados {}", topicoDestino, transacaoConecta.toJson());
            kafkaTemplate.send(topicoDestino, TransacaoConectaEncriptado.fromTransacao(transacaoConecta, chave).toJson());
            return true;
        } catch (Exception e) {
            log.error("Erro ao enviar dados ao topico {} com dados {}", topicoDestino, transacaoConecta.toJson(), e);
            return false;
        }
    }
}

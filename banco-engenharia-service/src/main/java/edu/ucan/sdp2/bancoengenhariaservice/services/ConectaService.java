package edu.ucan.sdp2.bancoengenhariaservice.services;

import edu.ucan.sdp2.bancoengenhariaservice.dto.Resposta;
import edu.ucan.sdp2.bancoengenhariaservice.dto.requisicoes.MovimentoRequisicaoDto;
import edu.ucan.sdp2.conectacore.listener.OuvinteAbstract;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class ConectaService {


    private final KafkaTemplate<String, String> kafkaTemplate;
    @Value("${topico.conecta-topico}")
    private String topico;

    @Autowired
    public ConectaService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public Resposta<MovimentoRequisicaoDto> enviarMovimento(MovimentoRequisicaoDto movimentoRequisicaoDto) {
        try {
            log.info("Enviando debito para o topico {} com dados {}", topico, movimentoRequisicaoDto.toJson());
            kafkaTemplate.send(topico, movimentoRequisicaoDto.toJson());
            return new Resposta<>(movimentoRequisicaoDto);
        } catch (Exception e) {
            log.error("Erro ao enviar dados ao topico {} com dados {}", topico, movimentoRequisicaoDto.toJson(), e);
            return new Resposta<>("Erro! Não foi possível efetuar a requisicao", null);
        }
    }
}

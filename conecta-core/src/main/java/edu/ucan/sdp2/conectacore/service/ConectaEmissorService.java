package edu.ucan.sdp2.conectacore.service;


import edu.ucan.sdp2.conectacore.enums.TipoOperacao;
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

    @Value("${topico.conecta-topico}")
    private String topicoConecta;
    @Value("${spring.kafka.template.default-topic}")
    private String topicoOrigem;
    @Value("${banco.identificador.chave}")
    private String chave;

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public ConectaEmissorService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public boolean enviarMovimentoToConnecta(TransacaoConecta transacaoConecta) {
        return   enviarMensagem(
                TransacaoConectaEncriptado.fromTransacao(transacaoConecta, chave).toJson(),
                topicoConecta,
                transacaoConecta.getTipoEvento().name());
    }

    public boolean enviarMovimentoToConnecta(TransacaoConecta transacaoConecta, String externalChave) {
        return   enviarMensagem(
                TransacaoConectaEncriptado.fromTransacao(transacaoConecta, externalChave).toJson(),
                topicoConecta,
                transacaoConecta.getTipoEvento().name());
    }



    public boolean enviarTransacao(TransacaoConecta transacaoConecta, String chave) {
        return   enviarMensagem(
                TransacaoConectaEncriptado.fromTransacao(transacaoConecta, chave).toJson(),
                transacaoConecta.getTopico(),
                transacaoConecta.getTipoEvento().name()
        );
    }


    public boolean enviarTransacao(TransacaoConecta transacaoConecta, String chave, String canal) {
        return   enviarMensagem(
                TransacaoConectaEncriptado.fromTransacao(transacaoConecta, chave).toJson(),
                canal,
                transacaoConecta.getTipoEvento().name()
        );
    }
    public boolean enviarMovimento(TransacaoConecta transacaoConecta, String chave, String topicoDestino) {
        return   enviarMensagem(
                TransacaoConectaEncriptado.fromTransacao(transacaoConecta, chave).toJson(),
                topicoDestino,
                transacaoConecta.getTipoEvento().name()
                );
    }


    public boolean enviarMovimento(TransacaoConectaEncriptado transacaoConectaEncriptado, String topico, String assunto ) {
        return   enviarMensagem(
                transacaoConectaEncriptado.toJson(),
                topico,
                assunto
        );
    }

    public boolean enviarMovimentoToConnecta(TransacaoConectaEncriptado transacaoConectaEncriptado, String assunto ) {
        return   enviarMensagem(
                transacaoConectaEncriptado.toJson(),
                topicoConecta,
                assunto
        );
    }
    private boolean enviarMensagem(String mensagem, String topicoDestino, String operacaoKey) {
        try {
            kafkaTemplate.send(topicoDestino, operacaoKey, mensagem);
            return true;
        } catch (Exception e) {
            log.error("Erro ao enviar dados ao topico {} com dados {}", topicoDestino,mensagem, e);
            return false;
        }
    }
}

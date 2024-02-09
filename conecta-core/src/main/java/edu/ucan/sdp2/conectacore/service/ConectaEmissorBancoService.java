package edu.ucan.sdp2.conectacore.service;


import edu.ucan.sdp2.conectacore.models.TransacaoConecta;
import edu.ucan.sdp2.conectacore.models.TransacaoConectaDetalhes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ConectaEmissorBancoService {



    @Value("${topico.conecta-topico}")
    private String topico;
    @Value("${topic-fail}")
    private String topicoFalha;
    @Value("${spring.kafka.template.default-topic}")
    private String topicoOrigem;

    @Value("${banco.identificador.chave}")
    private String chave;

    private final ConectaEmissorService conectaEmissorService;

    @Autowired
    public ConectaEmissorBancoService(ConectaEmissorService conectaEmissorService) {
        this.conectaEmissorService = conectaEmissorService;
    }

    public boolean enviarMovimento(TransacaoConectaDetalhes detalhes) {
        TransacaoConecta transacaoConecta = new TransacaoConecta();
        transacaoConecta.setDetalhes(detalhes);
        transacaoConecta.setTopico(topicoOrigem);
        return conectaEmissorService.enviarMovimento(transacaoConecta, chave, topico);
    }
}

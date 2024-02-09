package edu.ucan.sdp2.connecta.messages;

import edu.ucan.sdp2.conectacore.listener.OuvinteAbstract;
import edu.ucan.sdp2.conectacore.models.TransacaoConecta;
import edu.ucan.sdp2.conectacore.models.TransacaoConectaEncriptado;
import edu.ucan.sdp2.conectacore.service.ConectaEmissorService;
import edu.ucan.sdp2.conectacore.utils.JsonUtil;
import edu.ucan.sdp2.connecta.configuration.BancoMapa;
import edu.ucan.sdp2.connecta.enums.BancosTopicos;
import edu.ucan.sdp2.connecta.service.ServicoConta;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;



@Slf4j
@Component
public class Consumidor extends OuvinteAbstract {


    private final ConectaEmissorService conectaEmissorService;

    @Autowired
    public Consumidor(ConectaEmissorService conectaEmissorService) {
        this.conectaEmissorService = conectaEmissorService;
    }


    @Override
    public void escuta(String json) {
        log.info("Mensagem em conecta: {}", json);
        var transacaoEncriptado = TransacaoConectaEncriptado.fromJson(json);
        BancoMapa bancoOrigem = BancoMapa.fromTopico(transacaoEncriptado.getTopico());
        TransacaoConecta transacao = TransacaoConectaEncriptado.toTransacao(transacaoEncriptado, bancoOrigem.getChave());
        BancoMapa bancDestino = descobrirBanco(transacao);

        if (bancDestino == null) {
            conectaEmissorService.enviarMovimento(transacao, bancoOrigem.getChave(), bancoOrigem.getTopicoFalha());
        }else {
            ServicoConta.checarConta(bancDestino.getHost(), transacao.getDetalhes().getIban()).subscribe(response ->
                    {
                        if (response) {
                            conectaEmissorService.enviarMovimento(transacao, bancDestino.getChave(), bancDestino.getTopico());
                        }else {
                            conectaEmissorService.enviarMovimento(transacao, bancoOrigem.getChave(), bancoOrigem.getTopicoFalha());
                        }
                    }
            );
        }
    }

    @Override
    public void falha(String json) {

    }

    private BancoMapa descobrirBanco(TransacaoConecta transacaoConecta) {
        for(BancoMapa bancoMapa: BancoMapa.values()) {
            if(transacaoConecta.getDetalhes().getIban().startsWith(bancoMapa.name())){
                return bancoMapa;
            }
        }
        return null;
    }



}

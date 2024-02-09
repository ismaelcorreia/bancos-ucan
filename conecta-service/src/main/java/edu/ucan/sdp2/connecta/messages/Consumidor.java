package edu.ucan.sdp2.connecta.messages;

import edu.ucan.sdp2.conectacore.listener.OuvinteAbstract;
import edu.ucan.sdp2.conectacore.models.TransacaoConecta;
import edu.ucan.sdp2.conectacore.models.TransacaoConectaEncriptado;
import edu.ucan.sdp2.conectacore.utils.JsonUtil;
import edu.ucan.sdp2.connecta.configuration.BancoMapa;
import edu.ucan.sdp2.connecta.enums.BancosTopicos;
import lombok.SneakyThrows;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;



@Slf4j
@Component
public class Consumidor extends OuvinteAbstract {


    @SneakyThrows
    @Override
    public void escuta(String json) {
        log.info("Mensagem em conecta: {}", json);
//        var transacao = TransacaoConectaEncriptado.fromJson(json);

//        TransacaoConecta transacaoConecta = TransacaoConecta.fromJson(json);
//        if (transacaoConecta != null) {
//            return transacaoConecta.
//        }
    }

    @Override
    public void falha(String json) {

    }

    private void reencaminhar(TransacaoConecta transacaoConecta) {

        var detalhes = transacaoConecta.getDetalhes();
        BancoMapa bancoOrigem = BancoMapa.fromTopico(transacaoConecta.getTopico());
        BancoMapa bancoDestino = BancoMapa.fromTopico(detalhes.getIban());
        if (bancoDestino == BancoMapa.LIXEIRA) {
            bancoOrigem
        }
    }



}

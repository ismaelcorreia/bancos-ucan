package edu.ucan.sdp2.bancoengenhariaservice;


import edu.ucan.sdp2.bancocore.services.conecta.ConectaTransacaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BancoEngenhariaServiceApplicationTests {


    private final ConectaTransacaoService conectaTransacaoService;
    @Autowired
    public BancoEngenhariaServiceApplicationTests(ConectaTransacaoService conectaTransacaoService) {
        this.conectaTransacaoService = conectaTransacaoService;
    }

    @Value("${banco.topico.transferencia-sucesso}")
    private String topic;
    @Test
    void contextLoads() {
//        TransacaoConecta transacaoConecta = new TransacaoConecta();
//        transacaoConecta.setId(UUID.randomUUID());
//        transacaoConecta.setDestino("destino-teste");
//        transacaoConecta.setOrigem("Banco Engenharia");
//        transacaoConecta.setMoeda(Moeda.AOA);
//        transacaoConecta.setMontante(new BigDecimal(3000));
//        transacaoConecta.setStatus(StatusTransacao.Pendente);
//        transacaoConecta.setDataHoraTransacao(new Date());
//        transacaoConecta.setTipoOperacao(TipoOperacao.Transferencia);
//
//
//        producer.enviarEvento(transacaoConecta,
//                topic
//        );
    }


}

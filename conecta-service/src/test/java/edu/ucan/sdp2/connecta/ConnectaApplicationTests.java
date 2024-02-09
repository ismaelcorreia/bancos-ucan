package edu.ucan.sdp2.connecta;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ucan.sdp2.conectacore.enums.Moeda;
import edu.ucan.sdp2.conectacore.enums.StatusTransacao;
import edu.ucan.sdp2.conectacore.enums.TipoOperacao;
import edu.ucan.sdp2.conectacore.models.TransacaoConecta;
import edu.ucan.sdp2.connecta.messages.Producer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@SpringBootTest
@Component
public class ConnectaApplicationTests {

	private final Producer producer;
	@Autowired
	public ConnectaApplicationTests(Producer producer) {
		this.producer = producer;
	}

	@Value("${spring.kafka.template.default-topic}")
	private String topic;
	@Test
	void contextLoads() {
//		TransacaoConecta transacaoConecta = new TransacaoConecta();
//		transacaoConecta.setId(UUID.randomUUID());
//		transacaoConecta.setDestino("destino-teste");
//		transacaoConecta.setOrigem("origem-teste");
//		transacaoConecta.setMoeda(Moeda.AOA);
//		transacaoConecta.setMontante(new BigDecimal(3000));
//		transacaoConecta.setStatus(StatusTransacao.Pendente);
//		transacaoConecta.setDataHoraTransacao(new Date());
//		transacaoConecta.setTipoOperacao(TipoOperacao.Transferencia);
//
//
//		producer.enviarEvento(transacaoConecta,
//				topic
//		);
	}

	@Test
	void encriptar() {
		String chave;
		Object object;
		String resultado;
	}



	@Test
	void decriptar() {
		String chave;
		String encriptado;
		Object resultado;
	}

}

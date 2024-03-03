package edu.ucan.sdp2.connecta.demo.producers;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class ProducingBroadCast {
    public static void main(String[] args) {
        // Configurar as propriedades do produtor Kafka
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        // Criar o produtor Kafka
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        // Enviar algumas mensagens para o tópico "transacoes-bancarias"
        try {
            for (int i = 0; i < 10; i++) {
                String message = "Transacao bancaria ";
                producer.send(new ProducerRecord<>("transacoes-bancarias", Integer.toString(i), message));
                System.out.println("Mensagem enviada: " + message);
                Thread.sleep(1000); // Aguardar 1 segundo entre as mensagens
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Fechar o produtor Kafka
            producer.close();
        }
    }
}

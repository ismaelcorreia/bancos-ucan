package edu.ucan.sdp2.connecta.demo.producers;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaProducerExample {
    public static void main(String[] args) {
        // Configurar as propriedades do produtor Kafka
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", StringSerializer.class.getName());
        props.put("value.serializer", StringSerializer.class.getName());

        // Criar o produtor Kafka
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        // Enviar algumas mensagens para o tópico "transacoes-bancarias"
        try {
            for (int i = 0; i < 10; i++) {
                String message = "Transacao bancaria #" + i;
                String groupId = "group2"; // Alternar entre os grupos 1 e 2
//                String groupId = i % 2 == 0 ? "group1" : "group2"; // Alternar entre os grupos 1 e 2
                producer.send(new ProducerRecord<>("transacoes-bancarias", groupId, message)); // Usar groupId como chave
                System.out.println("Mensagem enviada: " + message + " para o grupo: " + groupId);
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

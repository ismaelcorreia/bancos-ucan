package edu.ucan.sdp2.connecta.demo.consumers;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class KafkaConsumerExample1 {

    public static void main(String[] args) {
        // Configurar as propriedades do consumidor Kafka
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "group1"); // Grupo de consumidores 1
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());

        // Criar o consumidor Kafka
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        // Inscrever-se no tópico "transacoes-bancarias"
        consumer.subscribe(Collections.singletonList("transacoes-bancarias"));

        // Consumir mensagens do tópico
        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                records.forEach(record -> {
                    System.out.println("Grupo 1 - Recebido: " + record.value());
                });
            }
        } finally {
            // Fechar o consumidor Kafka
            consumer.close();
        }
    }
}

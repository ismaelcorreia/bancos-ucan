package edu.ucan.sdp2.connecta.demo.consumers;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.*;
import java.util.Collections;
import java.util.Properties;

public class Consumer {

    public static void main(String[] args) {
        // Configurar as propriedades do consumidor Kafka
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test-group");
        props.put("key.deserializer", StringDeserializer.class);
        props.put("value.deserializer", StringDeserializer.class);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");

        // Criar o consumidor Kafka
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        // Inscrever-se no tópico "transacoes-bancarias"
        consumer.subscribe(Collections.singletonList("transacoes-bancarias"));

        // Consumir mensagens do tópico
        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(100);
                records.forEach(record -> {
                    System.out.printf("\nMessage(%s, %s)", record.key(), record.value());
                });
            }
        } finally {
            // Fechar o consumidor Kafka
            consumer.close();
        }
    }
}

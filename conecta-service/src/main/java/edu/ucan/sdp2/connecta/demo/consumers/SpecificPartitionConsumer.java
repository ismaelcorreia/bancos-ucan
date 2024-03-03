package edu.ucan.sdp2.connecta.demo.consumers;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class SpecificPartitionConsumer {
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

        // Obter informações sobre as partições do tópico
        List<PartitionInfo> partitions = consumer.partitionsFor("transacoes-bancarias");

        // Consumir de uma partição específica
        int partitionToConsume = 0; // Defina o número da partição que deseja consumir
        TopicPartition topicPartition = new TopicPartition("transacoes-bancarias", partitionToConsume);
        consumer.assign(Collections.singletonList(topicPartition));

        // Consume mensagens da partição específica
        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(100);
                records.forEach(record -> {
                    System.out.println("Recebido da partição " + record.partition() + ": " + record.value());
                });
            }
        } finally {
            // Fechar o consumidor Kafka
            consumer.close();
        }
    }
}

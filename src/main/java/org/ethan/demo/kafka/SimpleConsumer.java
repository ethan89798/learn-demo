package org.ethan.demo.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

public class SimpleConsumer {
    public static void main(String[] args) {
        Properties prop = new Properties();
        prop.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.218.21.88:9092");
        prop.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group");
        prop.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        prop.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        prop.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        prop.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

        Consumer<String, String> consumer = new KafkaConsumer<>(prop);
        consumer.subscribe(Arrays.asList("mytest"));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            records.forEach(record -> System.out.println(record.key() + ", " + record.value() + ", toString=" + record));
        }
    }
}

package org.ethan.demo.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class SimpleProducer {

    public static void main(String[] args) {
        Properties prop = new Properties();
        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.218.21.88:9092");
        prop.put(ProducerConfig.ACKS_CONFIG, "all");
        prop.put(ProducerConfig.RETRIES_CONFIG, 0);
        prop.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        prop.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        prop.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(prop);

        for (int i = 0; i < 10; i++) {
            producer.send(new ProducerRecord<>("mytest", "key-" + i, "value-" + i));
        }
        System.out.println("send finished!!");
        producer.close();
    }

}

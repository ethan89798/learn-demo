package org.ethan.demo.kafka.apache;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;

public class SimpleConsumer {
    public static void main(String[] args) {
        Properties prop = new Properties();
        // 配置kakfa集群
        prop.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.218.21.88:9092");
        // 消息者组ID
        prop.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group");

        // 设置earliest-从最开始进行消费, latest-从当前消费的开始消费
        prop.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        // 开启自动提交
        prop.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        // 自动提交的时间间隔, 这里要注意,这样设置可能会重复读取到offset的信息,如果有机器销毁时, offset没有及时更新到zookeeper中时,就会产生重复读取问题
        prop.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        // key的反序列化类
        prop.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        // value的反序列化的类
        prop.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

        // 自定义消费分区类
        prop.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, "");

        Consumer<String, String> consumer = new KafkaConsumer<>(prop);
        // 设置消费多个主题的消息,如果只有一个就写一个主题就可以了
        consumer.subscribe(Arrays.asList("mytest", "first", "second"));
//        consumer.subscribe(Arrays.asList("mytest"));

        // 指定主题和分区进行消费
//        consumer.assign(Collections.singletonList(new TopicPartition("mytest", 0)));
        // 指定主题和分区,并且指定开始索引
//        consumer.seek(new TopicPartition("mytest", 0), 2);


        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            records.forEach(record -> System.out.println(record.key() + ", " + record.value() + ", toString=" + record));
        }
    }
}

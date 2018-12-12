package org.ethan.demo.kafka.apache;

import org.apache.kafka.clients.producer.*;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class SimpleProducer {

    public static void main(String[] args) {
        Properties prop = new Properties();
        // kafka集群
        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.218.21.88:9092");
        // 应答级别
        prop.put(ProducerConfig.ACKS_CONFIG, "all");
        // 重试次数
        prop.put(ProducerConfig.RETRIES_CONFIG, 0);
        // 批量大小
        prop.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        // 提交延时
        prop.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        // 缓存
        prop.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        // key的序列化的类
        prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        // value的序列化类
        prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        // kafka的拦截器, 可以做日志,统计
        List<String> interceptors = Arrays.asList("org.ethan.demo.kafka.apache.TimeIntercetor", "org.ethan.demo.kafka.apache.CountIntercetor");
        prop.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, interceptors);

        // 进行自定义分区
//        prop.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, "org.ethan.demo.kafka.apache.CustomPartitioner");

        // 创建生产者对象
        Producer<String, String> producer = new KafkaProducer<>(prop);

        // 使用生产者发送信息到kafka集群
        for (int i = 0; i < 10; i++) {
//            producer.send(new ProducerRecord<>("mytest", "key-" + i, "value-" + i));
            producer.send(new ProducerRecord<>("mytest", "key-" + i, "value-" + i), (metadata, exception) -> {
                if (exception == null) {
                    System.out.println(metadata.partition() + "---" + metadata.offset());
                } else {
                    System.out.println("发送失败, 进行处理");
                }
            });
        }
        System.out.println("send finished!!");
        producer.close();
    }

}

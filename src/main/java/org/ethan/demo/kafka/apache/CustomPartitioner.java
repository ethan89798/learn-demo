package org.ethan.demo.kafka.apache;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

/**
 * 自定义进行分区数据归类
 */
public class CustomPartitioner implements Partitioner {

    private Map<String, ?> configs;

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        // 时行hash值归类到各个分区中
        return key.hashCode() % 3;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> configs) {
        this.configs = configs;
    }
}

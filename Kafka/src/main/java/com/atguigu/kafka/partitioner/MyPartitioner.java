package com.atguigu.kafka.partitioner;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

/**
 * @author yhm
 * @create 2021-02-23 10:16
 * 1. 实现接口Partitioner
 * 2. 实现接口的方法
 */
public class MyPartitioner implements Partitioner {
    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        // 判断消息是否包含atguigu
        if (value.toString().contains("atguigu"))
            return 0;
        else return 1;

    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}

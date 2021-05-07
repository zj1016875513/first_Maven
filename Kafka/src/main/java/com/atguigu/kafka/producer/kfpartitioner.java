package com.atguigu.kafka.producer;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

public class kfpartitioner implements Partitioner {
    public int partition(String s, Object o, byte[] bytes, Object o1, byte[] bytes1, Cluster cluster) {
        int partition;
        String msgValue=o1.toString();
        if (msgValue.contains("atguigu")){
            partition=0;
        }else{
            partition=1;
        }
        return partition;
    }

    public void close() {

    }

    public void configure(Map<String, ?> map) {

    }
}

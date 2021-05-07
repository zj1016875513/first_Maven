package com.atguigu.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class T {
    public static void main(String[] args) throws InterruptedException {
        // 1. 创建kafka生产者的配置对象
        Properties properties = new Properties();

        // 2. 给kafka配置对象添加配置信息
        properties.put("bootstrap.servers","hadoop102:9092");


        // 设置ack
        properties.put("acks", "all");

        // 重试次数
        properties.put("retries", 1);

        // 批次大小 默认16K
        properties.put("batch.size", 16384);

        // 等待时间
        properties.put("linger.ms", 1);

        // RecordAccumulator缓冲区大小 默认32M
        properties.put("buffer.memory", 33554432);

        // key,value序列化
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        // 3. 创建kafka生产者对象
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(properties);

        // 4. 调用send方法,发送消息
        for (int i = 0; i < 10; i++) {

            kafkaProducer.send(new ProducerRecord<String,String>("first","aaa" + i));

        }

        // 5. 关闭资源
        kafkaProducer.close();
    }

}

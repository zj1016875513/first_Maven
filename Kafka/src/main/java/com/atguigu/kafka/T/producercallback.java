package com.atguigu.kafka.T;

import org.apache.kafka.clients.producer.*;

import java.util.Arrays;
import java.util.Properties;

public class producercallback {
    public static void main(String[] args) throws InterruptedException {
        Properties properties = new Properties();
        properties.put("bootstrap.servers","hadoop102:9092");

        properties.put("acks", "all");
        properties.put("retries", 3);
        properties.put("batch.size", 16384);
        properties.put("linger.ms", 1);
        properties.put("buffer.memory", 33554432);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        //添加拦截器
        properties.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, Arrays.asList("com.atguigu.kafka.T.Numinterceptor","com.atguigu.kafka.T.Timeintercepter"));

        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(properties);
        for (int i = 0; i < 10; i++) {
            kafkaProducer.send(new ProducerRecord<String, String>("first", "zzzzatguigu" + i), new Callback() {
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if (e==null){
                        System.out.println("success->"+ recordMetadata.offset());
                    }else{
                        e.printStackTrace();
                    }
                }
            });
            Thread.sleep(2);
        }
        kafkaProducer.close();
    }
}

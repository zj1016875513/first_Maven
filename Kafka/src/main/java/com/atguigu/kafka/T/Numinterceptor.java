package com.atguigu.kafka.T;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

public class Numinterceptor implements ProducerInterceptor<String,String> {
    int success;
    int failed;
    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> producerRecord) {
        return producerRecord;
    }

    @Override
    public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {
        if(e!=null){
            failed++;
        }else {
            success++;
        }
    }

    @Override
    public void close() {
        System.out.println("success->"+success+"     failed->"+failed);
    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}

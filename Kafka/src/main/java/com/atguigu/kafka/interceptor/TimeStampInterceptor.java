package com.atguigu.kafka.interceptor;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

/**
 * @author yhm
 * @create 2021-02-23 14:07
 * 需求: 在数据前面添加时间戳
 */
public class TimeStampInterceptor implements ProducerInterceptor<String,String> {

    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> record) {
        // 获取信息
        String value = record.value();
        // 获取时间戳
        long m = System.currentTimeMillis();
        // 修改消息
        String newValue = m + value;

        // 创建新的record
        return new ProducerRecord<String,String>(record.topic(),record.partition(),record.timestamp(),record.key(),newValue,record.headers());
    }

    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {

    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}

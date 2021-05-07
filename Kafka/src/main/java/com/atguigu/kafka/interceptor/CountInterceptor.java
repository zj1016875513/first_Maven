package com.atguigu.kafka.interceptor;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.EmptyStackException;
import java.util.Map;

/**
 * @author yhm
 * @create 2021-02-23 14:07
 * 需求: 统计发送消息的成功条数和失败条数
 */
public class CountInterceptor implements ProducerInterceptor<String, String> {
    private int failed;
    private int success;

    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> record) {
        // 保存数据不变
        return record;
    }

    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {

        // 判断是否存在异常
        if (exception != null) {
            failed++;
        } else success++;

    }

    @Override
    public void close() {
        System.out.println("failed ->" + failed + "    success -> " + success);
    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}

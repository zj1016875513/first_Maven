package com.atguigu.kafka.partitioner;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;

/**
 * @author yhm
 * @create 2021-02-23 9:10
 */
public class CustomProducerCellBack {
    public static void main(String[] args) throws InterruptedException {
        // 创建一个配置类
        Properties properties = new Properties();

        // 添加配置信息给配置类
        // 必须的配置
        properties.put("bootstrap.servers","hadoop102:9092");
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"hadoop102:9092");

        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG,"com.atguigu.kafka.partitioner.MyPartitioner");

        // 非必须的配置
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



        // 创建一个KafkaProducer
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(properties);

        // 调用send方法发送消息
        for (int i = 0; i < 10; i++) {

            kafkaProducer.send(new ProducerRecord<String, String>("first", "qianlaoshiatguigu" + i), new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    // 判断是否存在异常
                    if (exception != null){
                        exception.printStackTrace();
                    }else {
                        System.out.println(metadata);
                    }
                }
            });
            Thread.sleep(2);

        }



        // 关闭资源
        kafkaProducer.close();


    }
}

package com.atguigu.day10

import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

import scala.util.Random

object KafkaProcesor {

  def main(args: Array[String]): Unit = {

    //设置生产者参数
    val props = new Properties()
    //指定key的序列化器
    props.setProperty("key.serializer","org.apache.kafka.common.serialization.StringSerializer")
    //指定value的序列化器
    props.setProperty("value.serializer","org.apache.kafka.common.serialization.StringSerializer")
    //指定kafka集群地址
    props.setProperty("bootstrap.servers","hadoop102:9092,hadoop103:9092,hadoop104:9092")

    val producer = new KafkaProducer[String,String](props)

    val areas = Array("华北","华南","华东","西南","西北","东北")

    val citys = Array("深圳","广州","杭州","上海","北京")
    while (true){
      //某个时间点 某个地区 某个城市 某个用户 某个广告
      val message = s"${System.currentTimeMillis()} ${areas( Random.nextInt(areas.length) )} ${citys( Random.nextInt(citys.length) )} ${Random.nextInt(10)} ${Random.nextInt(10)}"

      producer.send(new ProducerRecord[String,String]("stream",message))

      Thread.sleep(100)
    }
  }
}

package com.atguigu.day09

import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import org.apache.spark.streaming.{Seconds, StreamingContext}

object $04_KafkaSource {

  def main(args: Array[String]): Unit = {

    //创建StreamingContext
    val ssc = new StreamingContext(new SparkConf().setMaster("local[4]").setAppName("test"),Seconds(5))
    ssc.sparkContext.setLogLevel("error")
    //使用kafka数据源拉取数据
    //设置消费的topic名称
    val topics = Array[String]("spark_kafka_source")
    //设置消费者参数
    val params = Map[String,Object](
      //设置key的反序列化器
      "key.deserializer" -> "org.apache.kafka.common.serialization.StringDeserializer",
      //设置value的反序列化器
      "value.deserializer" -> "org.apache.kafka.common.serialization.StringDeserializer",
      //设置topic所在集群地址
      "bootstrap.servers" -> "hadoop102:9092,hadoop103:9092,hadoop104:9092",
      //消费者组的id
      "group.id" -> "ks01",
      //设置消费者组第一次开始消费topic的时候从哪个位置开始消费
      "auto.offset.reset" -> "earliest",
      //是否自动提交offset
      "enable.auto.commit" -> "true"
    )
    val ds = KafkaUtils.createDirectStream[String,String](ssc,LocationStrategies.PreferConsistent,ConsumerStrategies.Subscribe[String,String](topics,params))
    //数据处理
    //一旦调用action算子，执行完成之后，会自动提交offset
    //sparkstreaming消费kafka数据的时候,DStream的分区 = topic的分区数<会动态感知>
    ds.map(x=> x.value())
      .foreachRDD(rdd=>{
        println("分区数="+rdd.partitions.length)
        val result = rdd.flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_)
        result.foreach(println(_))
      })

    //结果展示


    //启动、阻塞
    ssc.start()
    ssc.awaitTermination()
  }
}

package com.atguigu.day09

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object $01_WordCount {

  def main(args: Array[String]): Unit = {

    //1、创建StreamingContext
    val conf = new SparkConf().setMaster("local[4]").setAppName("test")
    //设置批次时间 5s采集一个批次
    val ssc = new StreamingContext(conf,Seconds(5))
    //设置日志级别
    ssc.sparkContext.setLogLevel("error")
    //sparkstreaming是接受一个批次的数据就处理一个批次的数据
    //2、读取数据
    val ds = ssc.socketTextStream("hadoop102",9999)
    //3、数据处理
    //   切割、压平
    /*val ds2 = ds.flatMap(_.split(" "))
    //   转成KV
    val ds3 = ds2.map(x=>{
      Thread.sleep(7000)
      (x,1)
    })
    //   统计单词个数
    val ds4 = ds3.reduceByKey(_+_)
    //4、展示结果
    ds4.print()*/

    val ds5 = ds.transform(rdd=>{
        rdd.flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_)
    })
    ds5.print()
    //5、启动streaming程序
    ssc.start()
    //6、阻塞主程序
    ssc.awaitTermination()
  }
}

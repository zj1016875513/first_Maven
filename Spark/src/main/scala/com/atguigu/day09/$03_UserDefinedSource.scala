package com.atguigu.day09

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object $03_UserDefinedSource {

  def main(args: Array[String]): Unit = {

    //创建StreamingContext
    val ssc = new StreamingContext(new SparkConf().setMaster("local[4]").setAppName("test"),Seconds(5))
    ssc.sparkContext.setLogLevel("error")

    //从自定义数据源中读取数据
    val source = new UserDefinedSource("hadoop102",9999)
    val ds = ssc.receiverStream(source)
    //数据处理
    ds.flatMap(_.split(" "))
      .map((_,1))
      .reduceByKey(_+_)
      .print()
    //结果展示

    //启动
    ssc.start()

    //阻塞
    ssc.awaitTermination()
  }
}

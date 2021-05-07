package com.atguigu.day10

import java.net.URI

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object $01_StreamingClose {
//优雅的关闭
  def main(args: Array[String]): Unit = {

    val ssc = new StreamingContext(new SparkConf().setMaster("local[4]").setAppName("test"),Seconds(5))
    ssc.sparkContext.setLogLevel("error")
    val ds = ssc.socketTextStream("hadoop102",9999)

    ds.flatMap(_.split(" "))
      .map((_,1))
      .reduceByKey(_+_)
      .print()

    ssc.start()

    val fs = FileSystem.get(new URI("hdfs://hadoop102:8020"),new Configuration(),"atguigu")

    while(fs.exists(new Path("hdfs://hadoop102:8020/input"))){
      Thread.sleep(5000)
    }
    //stopGracefully = true: 代表将接受到的数据处理完成之后才会停止
    //stopGracefully = false: 代表将直接停止,不会等到接受到的数据处理完成之后才会停止
    //stopSparkContext : 代表是否停止sparkcontext
    ssc.stop(true,true)

    ssc.awaitTermination()

  }
}

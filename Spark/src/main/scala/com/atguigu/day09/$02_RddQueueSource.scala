package com.atguigu.day09

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.{Seconds, StreamingContext}

import scala.collection.mutable

object $02_RddQueueSource {

  def main(args: Array[String]): Unit = {

    //创建StreamingContext
    val ssc = new StreamingContext(new SparkConf().setMaster("local[4]").setAppName("test"),Seconds(5))
    ssc.sparkContext.setLogLevel("error")

    //从指定的rdd队列中获取数据
    val queue = mutable.Queue[RDD[String]]()
    //oneAtATime=true: 每个批次只处理一个队列中的一个RDD
    //oneAtATime=false: 每个批次处理的是该批次中所有的RDD
    val ds = ssc.queueStream(queue,false)

    //处理数据
    ds.flatMap(_.split(" "))
      .map((_,1))
      .reduceByKey(_+_)
      .print()

    //启动streaming程序
    ssc.start()

    for(i<- 1 to 20){
      val rdd = ssc.sparkContext.parallelize(List("hello java hello scala","hello spark"))
      queue.enqueue(rdd)
      Thread.sleep(2000)//每2秒给队列里放一个这个RDD，每5秒处理一批次，直到20个RDD都处理完之后，就没了
    }
    //阻塞
    ssc.awaitTermination()

  }
}

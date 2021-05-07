package com.atguigu.day09

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object $05_Window {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[4]").setAppName("test")
    //设置批次时间 5s一个批次
    val ssc = new StreamingContext(conf,Seconds(5))

    ssc.checkpoint("checkpoint")
    //设置日志级别
    ssc.sparkContext.setLogLevel("error")
    val ds = ssc.socketTextStream("hadoop102",9999)

/*    ds.flatMap(_.split(" "))
      .map((_,1))
      //窗口长度与滑动长度必须是批次时间的整数倍
      .window(Seconds(25),Seconds(5))
      .reduceByKey(_+_)
      .print()*/
    //窗口一般用于计算一定时间内的统计
  /*  ds.flatMap(_.split(" "))
        .map((_,1))
        .reduceByKeyAndWindow((agg:Int,curr:Int)=>agg+curr,Seconds(25),Seconds(5))
        .print()*/

    //反向计算
    ds.flatMap(_.split(" "))
      .map((_,1))
      .reduceByKeyAndWindow((agg:Int,curr:Int)=>{
        println(s"追加:agg=${agg}  curr=${curr}")
        agg+curr
      },(agg:Int,curr:Int)=>{
        println(s"滑出: agg=${agg} curr=${curr}")
        agg-curr

      },Seconds(25),Seconds(5))
      .print()

    ssc.start()

    ssc.awaitTermination()
  }
}

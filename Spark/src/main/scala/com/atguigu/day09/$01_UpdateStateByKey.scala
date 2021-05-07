package com.atguigu.day09

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object $01_UpdateStateByKey {
  /**
   * wordcount累加，即本次与上次一起计算
    * updateStateByKey能够统计全局结果
    * @param args
    */
  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setMaster("local[4]").setAppName("test")
    //设置批次时间 5s一个批次
    val ssc = new StreamingContext(conf,Seconds(5))

    //设置日志级别
    ssc.sparkContext.setLogLevel("error")
    //设置状态的保存位置
    ssc.checkpoint("checkpoint")
    val ds = ssc.socketTextStream("hadoop102",9999)

    val reduce = (currBathValues:Seq[Int],state:Option[Int]) =>{
        //取出本批次之前关于该key的统计结果
       val stateNum = state.getOrElse(0)
       //当前批次该key所有value值求和
       val currentNum = currBathValues.sum
      //之前批次加上当前批次总结果 = 该key的全局结果
       Some(stateNum+currentNum)
    }

    ds.flatMap(_.split(" "))
      .map((_,1))
      .updateStateByKey(reduce)
      .print()


    ssc.start()
    ssc.awaitTermination()
  }
}

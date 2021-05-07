package com.atguigu.day05

import org.apache.spark.{SparkConf, SparkContext}

object $09_Accumulator {

  def main(args: Array[String]): Unit = {

    val sc = new SparkContext(new SparkConf().setMaster("local[4]").setAppName("test"))
    val ac = sc.longAccumulator("acc")
    val rdd = sc.parallelize(List(100,100,100,100,100,100),2)
    //var sum = 0

    //rdd.foreach(x=> sum = sum+x)
    rdd.foreach(x=> {
      ac.add(x)
    })

    //获取最终累加结果
    println(ac.value)
    println(ac.avg)

    //println(sum)
//    Thread.sleep(100000)
  }
}

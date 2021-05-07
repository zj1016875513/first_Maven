package com.atguigu.day05

import org.apache.spark.{SparkConf, SparkContext}

object $03_Lineage {
//查看血缘关系
  def main(args: Array[String]): Unit = {

    val sc = new SparkContext(new SparkConf().setMaster("local[4]").setAppName("test"))

    val rdd = sc.textFile("Spark/datas/wc.txt")
    println(rdd.toDebugString)
    println("------------------------------------------------------")
    val rdd2= rdd.flatMap(_.split(" "))
    println(rdd2.toDebugString)
    println("------------------------------------------------------")
    val rdd3 = rdd2.map((_,1))
    println(rdd3.toDebugString)
    println("------------------------------------------------------")
    val rdd4 = rdd3.reduceByKey(_+_)
    println(rdd4.toDebugString)
    println("------------------------------------------------------")
    println(rdd4.count())
  }
}

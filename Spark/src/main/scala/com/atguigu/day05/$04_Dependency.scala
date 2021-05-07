package com.atguigu.day05

import org.apache.spark.{SparkConf, SparkContext}

object $04_Dependency {
  //查看依赖关系
  /**
    * RDD依赖关系分为: 宽依赖、窄依赖
    *     宽依赖:  有shuffle操作的称之为宽依赖
    *     窄依赖:  没有shuffle操作的称之为窄依赖
    * @param args
    */
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(new SparkConf().setMaster("local[3]").setAppName("test"))

    val rdd = sc.textFile("Spark/datas/wc.txt")
    println(rdd.dependencies.toList)
    println("------------------------------------------------------")
    val rdd2= rdd.flatMap(_.split(" "))
    println(rdd2.dependencies.toList)
    println("------------------------------------------------------")
    val rdd3 = rdd2.map((_,1))
    println(rdd3.dependencies.toList)
    println("------------------------------------------------------")
    val rdd4 = rdd3.reduceByKey(_+_)
    println(rdd4.dependencies.toList)
    println("------------------------------------------------------")
    println(rdd4.count())
    rdd4.foreach(println(_))
  }
}

package com.atguigu.day06

import org.apache.spark.{SparkConf, SparkContext}

object $01_Accumulator {

  /**
    * 累加器:
    *     好处: 在统计汇总的时候可以使用累加器减少shuffle操作
    *
    * 使用自定义累加器:
    *     1、创建自定义累加器对象
    *     2、注册累加器
    * @param args
    */
  def main(args: Array[String]): Unit = {

    val sc = new SparkContext(new SparkConf().setMaster("local[4]").setAppName("test"))
    val acc = new WordCountAccumulator

    sc.register(acc,"wc")
    val rdd = sc.textFile("datas/wc.txt")

    val rdd2 = rdd.flatMap(_.split(" "))

    val rdd3 = rdd2.map((_,1))

    //val rdd4 = rdd3.reduceByKey(_+_)

    rdd3.foreach(x=> {
      acc.add(x)
    })

    println(acc.value)
    //println(rdd4.collect().toList)

    Thread.sleep(100000)
  }
}

package com.atguigu.Book.accumulator

import org.apache.spark.rdd.RDD
import org.apache.spark.util.LongAccumulator
import org.apache.spark.{SparkConf, SparkContext}

object accumulator01_system {

  def main(args: Array[String]): Unit = {

    //1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[1]")

    //2.创建SparkContext，该对象是提交Spark App的入口
    val sc: SparkContext = new SparkContext(conf)

    //3.创建RDD
    val dataRDD: RDD[(String, Int)] = sc.makeRDD(List(("a", 1), ("a", 2), ("a", 3), ("a", 4)))

    //3.1 打印单词出现的次数（a,10） 代码执行了shuffle，效率比较低
    dataRDD.reduceByKey(_ + _).collect().foreach(println)

    //3.2 如果不用shuffle，怎么处理呢？
    var sum = 0
    // 打印是在Executor端
    dataRDD.foreach {
      case (a, count) => {
        sum = sum + count
        println("sum=" + sum)
      }
    }
    // 打印是在Driver端
    println(("a", sum))

    //3.3 使用累加器实现数据的聚合功能
    // Spark自带常用的累加器
    //3.3.1 声明累加器
    val sum1: LongAccumulator = sc.longAccumulator("sum1")

    dataRDD.foreach{
      case (a, count)=>{
        //3.3.2 使用累加器
        sum1.add(count)

        //3.3.3 不在Executor端读取累加器的值
        //println(sum1.value)
      }
    }
    //3.3.4 获取累加器
    println(sum1.value)

    //4.关闭连接
    sc.stop()
  }
}

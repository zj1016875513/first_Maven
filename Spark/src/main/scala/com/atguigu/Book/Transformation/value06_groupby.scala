package com.atguigu.Book.Transformation

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object value06_groupby {

  def main(args: Array[String]): Unit = {

    //1.创建SparkConf并设置App名称
    val conf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

    //2.创建SparkContext，该对象是提交Spark App的入口
    val sc = new SparkContext(conf)

    //3具体业务逻辑
    // 3.1 创建一个RDD
    val rdd = sc.makeRDD(1 to 4, 2)

    // 3.2 将每个分区的数据放到一个数组并收集到Driver端打印
    rdd.groupBy(_ % 2).collect().foreach(println)

    // 3.3 创建一个RDD
    val rdd1: RDD[String] = sc.makeRDD(List("hello", "hive", "hadoop", "spark", "scala"))

    // 3.4 按照首字母第一个单词相同分组
    rdd1.groupBy(str => str.substring(0, 1)).collect().foreach(println)

    sc.stop()
  }
}

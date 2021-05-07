package com.atguigu.Book.serializable

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

object serializable03_Kryo {

  def main(args: Array[String]): Unit = {

    val conf: SparkConf = new SparkConf()
      .setAppName("SerDemo")
      .setMaster("local[*]")
      // 替换默认的序列化机制
      .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
      // 注册需要使用kryo序列化的自定义类
      .registerKryoClasses(Array(classOf[Searche]))

    val sc = new SparkContext(conf)

    val rdd: RDD[String] = sc.makeRDD(Array("hello world", "hello atguigu", "atguigu", "hahah"), 2)

    val searche = new Searche("hello")
    val result: RDD[String] = searche.getMatchedRDD1(rdd)

    result.collect.foreach(println)
  }
}

case class Searche(val query: String) {

  def isMatch(s: String) = {
    s.contains(query)
  }

  def getMatchedRDD1(rdd: RDD[String]) = {
    rdd.filter(isMatch)
  }

  def getMatchedRDD2(rdd: RDD[String]) = {
    val q = query
    rdd.filter(_.contains(q))
  }
}


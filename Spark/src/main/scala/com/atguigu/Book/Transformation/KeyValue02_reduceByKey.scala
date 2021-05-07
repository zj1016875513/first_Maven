package com.atguigu.Book.Transformation

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object KeyValue02_reduceByKey {

  def main(args: Array[String]): Unit = {

    //1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[2]")

    //2.创建SparkContext，该对象是提交Spark App的入口
    val sc: SparkContext = new SparkContext(conf)

    //3具体业务逻辑
    //3.1 创建第一个RDD
    val rdd = sc.makeRDD(List(("a", 1), ("b", 5), ("a", 5), ("b", 2)))

    //3.2 计算相同key对应值的相加结果
    val reduce: RDD[(String, Int)] = rdd.reduceByKey((v1, v2) =>{
      println(s"v1=${v1}  v2=${v2}")
      v1 + v2
    })

    //3.3 打印结果
    reduce.collect().foreach(println)

    rdd.reduceByKey(_ + _).foreach(println(_)) //可简写为_+_  效果一样

    //4.关闭连接
    sc.stop()
  }
}

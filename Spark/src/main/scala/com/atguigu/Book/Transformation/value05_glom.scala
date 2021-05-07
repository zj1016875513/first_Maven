package com.atguigu.Book.Transformation

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object value05_glom {

  def main(args: Array[String]): Unit = {

    //1.创建SparkConf并设置App名称
    val conf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

    //2.创建SparkContext，该对象是提交Spark App的入口
    val sc = new SparkContext(conf)

    //3具体业务逻辑
    // 3.1 创建一个RDD
    val rdd = sc.makeRDD(1 to 4, 2)

    // 3.2 求出每个分区的最大值  0->1,2   1->3,4
    val maxRdd: RDD[Int] = rdd.glom().map(_.max)

    // 3.3 求出所有分区的最大值的和 2 + 4
    println(maxRdd.collect().sum)

    //4.关闭连接
    sc.stop()
  }
}

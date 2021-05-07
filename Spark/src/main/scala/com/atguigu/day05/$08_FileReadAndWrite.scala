package com.atguigu.day05

import org.apache.spark.{SparkConf, SparkContext}

object $08_FileReadAndWrite {

  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(new SparkConf().setMaster("local[4]").setAppName("test"))

    //读取文本文件
    val rdd = sc.textFile("Spark/datas/wc.txt")
    rdd.foreach(println(_))

    //sc.objectFile()

    //保存成文件文件
    //rdd.saveAsTextFile("output/text")
    //保存成对象文件
    //rdd.saveAsObjectFile("output/obj")
    //保存成序列化文件
    rdd.map((_,1)).saveAsSequenceFile("Spark/output/seq")

    //rdd.map((_,1))

  }
}

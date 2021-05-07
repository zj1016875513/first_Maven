package com.atguigu.day05

import org.apache.spark.{RangePartitioner, SparkConf, SparkContext}

object $07_RangePartitioner {

  def main(args: Array[String]): Unit = {

    val sc = new SparkContext(new SparkConf().setMaster("local[4]").setAppName("test"))

    val rdd = sc.parallelize(List("tt"->3,"uu"->4 ,"pp"->5,"cc"->2,"aa"->1,"ll"->3,"kk"->4 ,"hh"->5,"gg"->2,"ff"->1 ),3)

    val rdd3 = rdd.partitionBy(new RangePartitioner(3,rdd))

    rdd3.mapPartitionsWithIndex((index,it)=>{
      println(s"index:${index} datas:${it.toList}")
      it
    }).collect
  }
}

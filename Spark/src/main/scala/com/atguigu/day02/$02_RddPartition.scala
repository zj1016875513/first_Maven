package com.atguigu.day02

import org.apache.spark.{SparkConf, SparkContext}
import org.junit.Test

class $02_RddPartition {

  val sc = new SparkContext(new SparkConf().setMaster("local[4]").setAppName("rddpartition")/*.set("spark.default.parallelism","50")*/)
  /**
    * 根据集合创建rdd,rdd的分区数
    *     没有设置spark.default.parallelism:
      *      本地执行:
      *          默认分区数 =
      *              1、setMaster("local")，默认分区数 = 1
      *              2、setMaster("local[N]")，默认分区数 = N
      *              3、setMaster("local[*]")，默认分区数 = 当前机器的cpu总核数
      *      集群执行:
      *          默认分区数 =  math.max(当前任务cpu总核数, 2)
    *   设置spark.default.parallelism参数之后，rdd分区数 = spark.default.parallelism的值
    *
    */
  @Test
  def createRddByCollectionPartition():Unit = {

    val rdd = sc.parallelize(Array(10,2,3,4,5,6,7,8,9),5)

    //查看rdd分区数
    val partitions = rdd.partitions
    println(partitions.size)   //rdd.partitions返回的是一个数组里面含有分区的编号
    rdd.collect()
  }

  /**
    * 根据其他rdd衍生的rdd的分区数默认 = 父rdd的分区数
    */
  @Test
  def createRddByRdd() = {
    val rdd = sc.parallelize(Array(10,2,3,4,5,6,7,8,9))

    val rdd2 = rdd.map(x=>x*x)

    println(rdd.partitions.size)
    println(rdd2.partitions.size)   //都是4    跟环境配置一样
  }

  /**
    * 根据文件创建rdd
    *     默认最小分区数:
    *         集群执行: math.min( math.max(当前任务cpu总核数, 2) , 2)
    *         本地执行: math.min( local[N]中的N , 2)
    */
  @Test
  def createRddByFile():Unit = {

    val rdd = sc.textFile("datas/wc.txt",4)  //大于配置分区数就为  n+1  小于等于配置分区数就为n

    println(rdd.partitions.size)
  }
}

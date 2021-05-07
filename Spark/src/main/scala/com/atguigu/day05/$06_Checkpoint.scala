package com.atguigu.day05

import org.apache.spark.{SparkConf, SparkContext}

object $06_Checkpoint {

  /**
    * checkpoint: 将数据持久化到可靠的存储介质[HDFS]中。
    *     使用:
    *       1、设置checkpoint数据保存路径: sc.setCheckpointDir("checkpoint")
    *       2、数据持久化: rdd.checkpoint()
    *     checkpoint会在第一个job执行完成之后，也会产生一个job<该job由checkpoint产生>，所以为了提高效率，一般checkpoint是结合缓存使用,在checkpoint之前先将rdd缓存起来,后续checkpoint产生的job会直接使用缓存数据
    *
    * cache与checkpoint的区别:
    *     1、数据持久化位置不一样:
    *           cache是将数据持久化到内存或者本地磁盘中，如果服务器宕机数据会丢失。
    *           checkpoint是将数据持久化到HDFS中，数据安全性更高
    *     2、cache会保留血统,checkopint会切断RDD的依赖
    *           cache会丢失数据，数据丢失之后需要根据依赖关系重新计算数据，所以此时依赖关系不能切除
    *           checkpoint不会丢失数据，所以可以切掉依赖关系，减少空间的占用
    */
  def main(args: Array[String]): Unit = {

    val sc = new SparkContext(new SparkConf().setMaster("local[4]").setAppName("TEST"))
    sc.setCheckpointDir("Spark/checkpoint")

    val rdd = sc.parallelize(List("hello java hello scala","hello java scala python"))

    val rdd2 = rdd.flatMap(_.split(" "))

    val rdd3 = rdd2.map(x=>{
      println("-----------------------------------")
      x.charAt(0)
    })
    println(rdd3.toDebugString)
    println("-----------------------------------")
    println(rdd3.dependencies.toList)
    //val rdd6 = rdd3.cache()
    rdd3.checkpoint()


    val rdd4 = rdd3.mapPartitions(it=>{
      it.filter(x=>x=='h')
    })

    println(rdd4.collect().toList)

    println(rdd3.toDebugString)
    println("-----------------------------------")
    println(rdd3.dependencies.toList)

    val rdd5 = rdd3.map(x=>x.hashCode())
    println(rdd5.toDebugString)
    println(rdd5.collect().toList)

    Thread.sleep(1000000)
  }
}

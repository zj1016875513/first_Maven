package com.atguigu.Book.cache

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object checkpoint02 {

  def main(args: Array[String]): Unit = {

    // 设置访问HDFS集群的用户名
    System.setProperty("HADOOP_USER_NAME","atguigu")

    //1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

    //2.创建SparkContext，该对象是提交Spark App的入口
    val sc: SparkContext = new SparkContext(conf)

    // 需要设置路径.需要提前在HDFS集群上创建/checkpoint路径
    sc.setCheckpointDir("hdfs://hadoop102:8020/checkpoint")

    //3. 创建一个RDD，读取指定位置文件:hello atguigu atguigu
    val lineRdd: RDD[String] = sc.textFile("input1")

    //3.1.业务逻辑
    val wordRdd: RDD[String] = lineRdd.flatMap(line => line.split(" "))

    val wordToOneRdd: RDD[(String, Long)] = wordRdd.map {
      word => {
        (word, System.currentTimeMillis())
      }
    }

    //3.4 增加缓存，避免再重新跑一个job做checkpoint
    wordToOneRdd.cache()

    //3.3 数据检查点：针对wordToOneRdd做检查点计算
    wordToOneRdd.checkpoint()

    //3.2 触发执行逻辑
    wordToOneRdd.collect().foreach(println)

    //4.关闭连接
    sc.stop()
  }
}


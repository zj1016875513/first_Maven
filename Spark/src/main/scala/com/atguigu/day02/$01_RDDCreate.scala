package com.atguigu.day02

import org.apache.spark.{SparkConf, SparkContext}
import org.junit.Test

class $01_RDDCreate {
  //什么是RDD?弹性分布式数据集，一种数据处理的"模型"&"数据结构"
  //创建SparkContext，该对象是提交Spark App的入口
  val conf = new SparkConf().setMaster("local[4]").setAppName("rddcreate")
  val sc = new SparkContext(conf)
  /**
    * 创建rdd的三种方式:
    *     1、根据集合创建rdd
    *     2、读取外部文件创建rdd
    *     3、通过其他rdd创建rdd
    */

  /**
    * 根据集合创建RDD
    *     一般用于测试
    */
  @Test
    def createRddByCollection() = {

    /*
    从集合中创建RDD，Spark主要提供了两种函数：parallelize和makeRDD
    makeRDD有两种重构方法，重构方法一如下，makeRDD和parallelize功能一样
    重构方法二，增加了位置信息 道makeRDD不完全等于parallelize
     */
      //创建rdd
      val arr = Array("hello","spark java scala","scala java hello")
      val rdd = sc.makeRDD(arr)
      println(rdd.collect().toList)

      val rdd2 = sc.parallelize(arr)
      println(rdd2.collect().toList)
    //List(hello, spark java scala, scala java hello)
    //List(hello, spark java scala, scala java hello)
    }

  /**
    * 根据读取外部文件创建rdd
    *     1、有配置/opt/modul/spark/conf/spark-env.sh文件下的HADOOP_CONF_DIR<一般工作中已经配置了>
    *         在集群中读取文件默认读取的是HDFS文件
    *         读取HDFS文件的方式:
    *             1、sc.textFile("/xx/xx") <常用>
    *             2、sc.textFile("hdfs://hadoop102:8020/xx/xx")
    *             3、sc.textFile("hdfs:///xx/xx")
    *         读取本地文件:
    *             sc.textFile("file:///xx/xxx")
    *     2、没有配置HADOOP_CONF_DIR
    *         在集群中默认读取的是本地文件
    *         读取HDFS文件的方式:
    *           1、sc.textFile("hdfs://hadoop102:8020/xx/xx")
    *         读取本地文件:
    *           1、sc.textFile("/xx/xx")
    *           2、sc.textFile("file:///xx/xxx")
    *
    */
  @Test
  def createRddByFile() = {
      val rdd = sc.textFile("hdfs://hadoop102:8020/wc.txt")

      println(rdd.collect().toList)
    //List(i am a student my name is zhangsan i love my hometown)
  }

  /**
    * 根据其他rdd衍生
    */
  @Test
  def createRddByRdd() = {
    val rdd = sc.textFile("hdfs://hadoop102:8020/wc.txt")

    val rdd2 = rdd.flatMap(_.split(" "))
    println(rdd2.collect().toList)
    //List(i, am, a, student, my, name, is, zhangsan, i, love, my, hometown)
  }
}

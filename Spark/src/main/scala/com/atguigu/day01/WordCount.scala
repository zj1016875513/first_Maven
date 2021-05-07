package com.atguigu.day01

import org.apache.spark.{SparkConf, SparkContext}

object WordCount {

  def main(args: Array[String]): Unit = {

    //1、创建SparkContext，该对象是提交Spark App的入口
    //   master: local/local[N]/local[*]
    //  提交代码到集群执行的时候,setMaster不需要设置，后续通过spark-submit --master 指定
    val conf = new SparkConf().setMaster("local[4]").setAppName("wordcount")
    val sc = new SparkContext(conf)
    //2、读取文件
    val rdd1 = sc.textFile(args(0))
    //3、切分、压平
    //val rdd2 = rdd1.flatMap(line=>line.split(" "))
    val rdd2 = rdd1.flatMap(_.split(" "))

    //4、分组+聚合
    //val rdd3 = rdd2.groupBy(x=>x)

    //val rdd4 = rdd3.map(x=>(x._1,x._2.size))
    val rdd3 = rdd2.map(x=>(x,1))

    val rdd4 = rdd3.reduceByKey((agg,curr)=> agg+curr)

    //5、结果展示
    val result = rdd4.collect()
    println(result.toList)

    sc.stop()
  }
}

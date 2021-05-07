package com.atguigu.day07

import org.apache.spark.sql.SparkSession

object $10_UDAFFunction {
  /**
    * 弱类型方式:
    *
    * 强类型方式:
    */
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local[4]").appName("test").getOrCreate()

    import spark.implicits._
    val df = List(
      (1,"lisi",20,"shenzhen"),
      (2,"aa",50,"beijing"),
      (3,"bb",30,"shenzhen"),
      (4,"cc",42,"beijing"),
      (5,"dd",86,"shenzhen"),
      (6,"ee",70,"beijing")
    ).toDF("id","name","age","region")

    //需求: 统计每个区域的平均年龄
    df.createOrReplaceTempView("person")

    //spark.sql("select region,avg(age) from person group by region").show

    //注册udaf函数<弱类型UDAF函数注册>
    val obj = new MyAvgUDAF
    spark.udf.register("myavg",obj)

    spark.sql("select region,myavg(age) from person group by region").show

    //注册udaf函数<强类型UDAF函数注册>
    val avg = new MyAvg
    import org.apache.spark.sql.functions._
    val function = udaf(avg)
    spark.udf.register("myavg2",function)
    spark.sql("select region,myavg2(age) from person group by region").show
  }
}

package com.atguigu.day07

import org.apache.spark.sql.SparkSession

object $07_Row {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().master("local[4]").appName("test").getOrCreate()

    import spark.implicits._
    val df = spark.read.textFile("datas/wc.txt").toDF()

    val rdd = df.rdd


    val rdd2= rdd.flatMap(row=>{
      //row类型取值
      val value = row.getAs[String]("value")
      value.split(" ")
    })

    val rdd3 = rdd2.map((_,1))

    val df2 = rdd3.toDF("word","num")

    df2.createOrReplaceTempView("wc")

    spark.sql("select word,count(1) from wc group by word").show()

  }
}

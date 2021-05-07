package com.atguigu.day07

import org.apache.spark.sql.{DataFrame, SparkSession}
case class Student(id:Int,name:String,age:Int)
object $05_RddDataFrameDataSet {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().master("local[4]").appName("test").getOrCreate()

    import spark.implicits._
    //rdd转DataFrame <toDF>
    val rdd = spark.sparkContext.parallelize(List((1,"zhangsan",20),(2,"lisi",30),(3,"wangwu",40)))

    val df: DataFrame = rdd.toDF

    //DataFrame转Rdd
    val rdd2 = df.rdd

    rdd2.collect().foreach(println(_))

    //rdd转DataSet
    val ds = rdd.toDS()

    //dataSet转rdd
    val rdd3 = ds.rdd

    //dataFrame转Dataset 【如果要转成DataSet[样例类]的形式,在转换的时候是根据列名与样例类属性匹配的,如果是转成DataSet[元组],元组的个数必须和列的个数一致】
    val ds3 = df.toDF("id","name","age").as[(Int,String,Int)]
    //val ds3 = df.toDF("id","name","age").as[Student]

    ds3.show

    //dataset转DataFrame
    val df4 = ds3.toDF()

  }
}

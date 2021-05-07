package com.atguigu.day07

import org.apache.spark.sql
import org.apache.spark.sql.SparkSession

object $01_SparkSessionCreate {


  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().master("local[4]").appName("test").getOrCreate()

    //val spark2 = new sql.SparkSession.Builder().master("local[4]").appName("test").getOrCreate()
    import spark.implicits._
  }
}

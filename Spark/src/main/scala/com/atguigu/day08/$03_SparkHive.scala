package com.atguigu.day08

import org.apache.spark.sql.{SaveMode, SparkSession}

object $03_SparkHive {

  def main(args: Array[String]): Unit = {

    System.setProperty("HADOOP_USER_NAME","atguigu")
    val spark = SparkSession.builder().master("local[4]").appName("test")
      .enableHiveSupport() //开启hive支持
      .getOrCreate()


    //读取hive数据
    val df = spark.sql("select * from school")

    //保存数据到hive<不常用>
    df.write.mode(SaveMode.Overwrite).saveAsTable("school_new")
  }
}

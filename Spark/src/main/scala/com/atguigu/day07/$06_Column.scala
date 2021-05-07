package com.atguigu.day07

import org.apache.spark.sql.{Column, SparkSession}

object $06_Column {

  /**
    * Column创建方式:
    *     1、无绑定
    *         1、通过 '列名 创建
    *         2、通过 $"列名" 创建
    *         3、通过 col(列名) 创建
    *         4、通过 column(列名) 创建
    *     2、有绑定
    */
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().master("local[4]").appName("test").getOrCreate()
    import spark.implicits._
    val df =List(
      Person("lisi",20),
      Person("zhangsan",30),
      Person("wangwu",40),
      Person("zhaoliu",100)
    ).toDF

    val df2 =List(
      Person("lisi",20),
      Person("zhangsan",30),
      Person("wangwu",40),
      Person("zhaoliu",100)
    ).toDF

    //通过 '列名 创建
    val column1:Column = 'name

    //通过 $"列名" 创建
    val column2:Column = $"name"

    //通过 col(列名) 创建
    import org.apache.spark.sql.functions._
    val column3 = col("name")

    //通过 column(列名) 创建
    val column4 = column("name")

    df.select(column1).show()

    //有绑定
    //df.col(列名)
    val column5 = df.col("name")
    df2.select(column5).show()


    val column6 = df("name")
    df.join(df2,df.col("name")===df2.col("name"))
  }
}

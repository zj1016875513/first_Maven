package com.atguigu.day07

import org.apache.spark.sql.SparkSession
import org.junit.Test

class $04_DataSetCreate {

  val spark = SparkSession.builder().master("local[4]").appName("test").getOrCreate()

  import spark.implicits._
  /**
    * DataSet的创建方式:
    *     1、toDS方法创建:
    *         集合.toDS
    *         rdd.toDS
    *     2、读取文本文件创建DataSet
    *     3、其他DataSet衍生
    *
    */

  /**
    * toDS方法创建
    */
  @Test
  def createDatasetByToDS(): Unit ={

    val list = List(
      Person("zhangsan",20),
      Person("lisi",25),
      Person("wangwu",30),
      Person("zhaoliu",18)
    )

    val ds = list.toDS()

    ds.show()

    val rdd = spark.sparkContext.parallelize(list)

    val ds2 = rdd.toDS()

    ds2.show

    ds2.map(x=>x.name)
  }

  @Test
  def createDataSetByFile(): Unit ={

    val ds = spark.read.textFile("datas/wc.txt")

    val ds2 = ds.flatMap(line=>line.split(" "))

    ds2.createOrReplaceTempView("wc")

    spark.sql(
      """
        |select value,count(1)
        | from wc group by value
      """.stripMargin).show
    //ds.show()
  }
}

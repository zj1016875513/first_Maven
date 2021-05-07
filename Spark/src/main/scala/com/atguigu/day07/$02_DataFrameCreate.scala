package com.atguigu.day07

import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{Row, SparkSession}
import org.junit.Test
case class Person( name:String, age:Int)

class $02_DataFrameCreate {

  val spark = SparkSession.builder().master("local[4]").appName("test").getOrCreate()

  import spark.implicits._
  /**
    * DataFrame创建:
    *     1、通过toDF创建:
    *         集合.toDF()
    *         rdd.toDF()
    *     2、读取文件创建: spark.read.json/csv/jdbc
    *     3、通过其他dataFrame衍生
    */

  /**
    * 通过toDF创建:
    *     1、如果集合/rdd中的类型是样例类,会直接将样例类的属性名作为列名
    *     2、如果集合/rdd中的类型是元组,默认的列名为_1,_2此种方式，可以通过toDF(列名,列名)的方式重定义列名
    */
  @Test
  def createDataFrameByToDF(): Unit ={
   /*   val list = List(
        Person("lisi",20),
        Person("zhangsan",30),
        Person("wangwu",40),
        Person("zhaoliu",100)
      )*/

    val list = List(
      ("lisi",20),
      ("zhangsan",30),
      ("wangwu",40),
      ("zhaoliu",100)
    )

    val df = list.toDF("name","age")

    //df.printSchema()
    //df.show()


    val rdd = spark.sparkContext.parallelize(list)

    val df3 = rdd.toDF()

    df3.show()
  }

  /**
    * 根据读取文件创建、衍生DataFrame
    */
  @Test
  def createDataFrameByFile(): Unit ={

    val df = spark.read.option("sep"," ").csv("datas/agent.log")
    val dfs2 = df.toDF("timestr","city","region","id","xx")

    df.show
    dfs2.show
  }

  @Test
  def createDataFrame3(): Unit ={

    val rdd = spark.sparkContext.parallelize(List(
      Row(1,"zhangsan",20),
      Row(2,"aa",32),
      Row(3,"bb",24),
      Row(3,"cc",26)
    ))
    val arr = Array(StructField("id",IntegerType),StructField("name",StringType),StructField("age",IntegerType))
    val schema = StructType(arr)
    spark.createDataFrame(rdd,schema).show
  }
}

package com.atguigu.day08

import java.util.Properties

import org.apache.spark.sql.{SaveMode, SparkSession}
import org.junit.Test

class $01_FileReader {

  val spark = SparkSession.builder().master("local[4]").appName("test").getOrCreate()

  /**
    * spark加载数据的方式:
    *     1、spark.read
    *           .option("","")... --设置读取需要的参数
    *           .format("") --设置读取数据的类型
    *           .load()/.load(path) --设置加载指定路径的数据
    *     2、spark.read.option()...csv/json/jdbc/..
    */
  @Test
  def readFile(): Unit ={
    //读取文本数据
    //第一种方式
    spark.read.format("text").load("datas/wc.txt")//.show
    //第二种形式
    spark.read.textFile("datas/wc.txt")//.show

    //读取csv数据<只要字段与字段之间有固定的分隔符就可以用csv读取>
    //第一种形式
    //csv常用的option参数:
    //  sep: 指定字段之间的分隔符
    //  header: 指定是否以文件的一行作为列名
    //  inferSchema: 是否自动推断列的类型
    val df = spark.read.format("csv").option("inferSchema","true").option("header","true").load("datas/presidential_polls.csv")
    df.printSchema()
    // 第二种方式
    spark.read.option("header","true").csv("datas/presidential_polls.csv")//.show
    spark.read.option("sep","\t").csv("datas/product.txt")//.toDF("....")

    //读取json数据
    //第一种方式
    spark.read.format("json").load("datas/pmt.json")//.show

    //第二种方式
    spark.read.json("datas/pmt.json")//.show()

    //读取parquet<spark读取写入默认是parquet格式>
    //spark.read.json("datas/pmt.json").write.mode(SaveMode.Overwrite).save("output/parquet")
    spark.read/*.format("parquet")*/.load("output/parquet").show

    spark.read.parquet("output/parquet").show()

  }

  @Test
  def readJdbc1(): Unit ={

    //第一种方式
    spark.read.format("jdbc")
      .option("user","root")
      .option("password","root123")
      .option("url","jdbc:mysql://hadoop102:3306/gmall")
      .option("dbtable","sku_image")
      .load()
      //.show()

    //第二种方式
    //
    val prop = new Properties
    prop.setProperty("user","root")
    prop.setProperty("password","root123")
    //此种方式生成的DataFrame只有一个分区 <数据量特别小可以使用>
    val df2 = spark.read.jdbc("jdbc:mysql://hadoop102:3306/gmall","sku_image",prop)//.show

    println(df2.rdd.partitions.length)

    val arr = Array[String]("id<20","id>=20 and id<40","id>=40")
    //此种方式生成的DataFrame的分区数 = 条件的个数 <工作不常用>
    val df = spark.read.jdbc("jdbc:mysql://hadoop102:3306/gmall","sku_image",arr,prop)

    println(df.rdd.partitions.length)
    //df.write.mode(SaveMode.Overwrite).csv("output/csv")

    //工作常用，
    //  分区数 = (upperBound - lowerBound)>=numPartitions ? numPartitions : upperBound-lowerBound
    //动态查询lowerBound
    val minDF = spark.read.jdbc("jdbc:mysql://hadoop102:3306/gmall","(SELECT MIN(id) minid FROM sku_image) as minid_table",prop)
    val minRow = minDF.rdd.first()
    val minid = minRow.getAs[Long](0)

    //动态查询upperBound
    val maxDF = spark.read.jdbc("jdbc:mysql://hadoop102:3306/gmall","(SELECT Max(id) maxid FROM sku_image) as maxid_table",prop)
    val maxRow = maxDF.rdd.first()
    val maxid = maxRow.getAs[Long](0)
    val df3 = spark.read.jdbc("jdbc:mysql://hadoop102:3306/gmall","sku_image","id",minid,maxid,50,prop)
    println(df3.rdd.partitions.length)
     df3 .write.mode(SaveMode.Overwrite).csv("output/csv")
  }
}

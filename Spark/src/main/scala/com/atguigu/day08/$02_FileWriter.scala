package com.atguigu.day08

import java.sql.{Connection, DriverManager, PreparedStatement}
import java.util.Properties

import org.apache.spark.sql.{SaveMode, SparkSession}
import org.junit.Test

class $02_FileWriter {

  val spark = SparkSession.builder().master("local[4]").appName("test").getOrCreate()

  import spark.implicits._
  /**
    * 两种方式:
    *     1、df/ds.write
    *           .mode() --指定写入模式
    *           .format() --指定数据写入格式
    *           .option() --指定数据写入的参数
    *           .save(path) --指定数据写入的路径
    *     2、df/ds.write.mode().csv/json/jdbc
    *
    *  SaveMode: 写入模式
    *     Append: 如果写入路径已经存在,追加数据到目录中
    *     Overwrite: 如果写入路径已经存在则覆盖数据
    *     ErrorIfExists: 默认，如果写入路径已经存在则报错
    *     Ignore: 如果写入路径已经存在则忽略
    *
    *     Overwrite、Append是工作常用:
    *         Overwrite一般用于将数据写入HDFS
    *         Append一般用于将数据写入mysql
    *
    */
  @Test
  def dataWrite(): Unit ={
    val datas = spark.read.textFile("datas/wc.txt")
    //写成文本
    //datas.write.mode(SaveMode.Overwrite).format("text").save("output/text")
    //datas.write.mode(SaveMode.Overwrite).text("output/text")

    //datas.write.mode(SaveMode.Overwrite).format("json").save("output/text")
    //datas.write.mode(SaveMode.Overwrite).json("output/text")

    //写成csv
    // option:
    //    header:
    //    sep:
    val csvdata = spark.read.option("header","true").csv("datas/presidential_polls.csv")
    //csvdata.write.mode(SaveMode.Overwrite).format("csv").save("output/csv")


    val prop = new Properties
    prop.setProperty("user","root")
    prop.setProperty("password","root123")
    //csvdata.write.mode(SaveMode.Append).jdbc("jdbc:mysql://hadoop102:3306/test","presidential_polls",prop)


    val df3 = spark.sparkContext.parallelize(List(
      ("20210320",1,2,30),
      ("20210320",2,3,30),
      ("20210320",3,4,30)
    )).toDF("dt","userid","adid","count")

    //df3.write.mode(SaveMode.Append).jdbc("jdbc:mysql://hadoop102:3306/test","user_ad_count",prop)

    df3.rdd.foreachPartition(it=>{
      var connection:Connection = null
      var statement:PreparedStatement = null
      try{
        connection = DriverManager.getConnection("jdbc:mysql://hadoop102:3306/test","root","root123")
        statement = connection.prepareStatement("""INSERT INTO user_ad_count VALUES(?,?,?,?) ON DUPLICATE KEY UPDATE  `count`=`count`+?""")
        it.foreach(row=>{
          val dt = row.getAs[String]("dt")
          val userid = row.getAs[Int]("userid")
          val adid = row.getAs[Int]("adid")
          val count = row.getAs[Int]("count")
          statement.setString(1,dt)
          statement.setInt(2,userid)
          statement.setInt(3,adid)
          statement.setInt(4,count)
          statement.setInt(5,count)
          statement.executeUpdate()
        })
      }catch {
        case e:Exception =>  e.printStackTrace()
      }finally {
        if(statement!=null)
          statement.close()
        if(connection!=null)
          connection.close()
      }
    })

  }
}

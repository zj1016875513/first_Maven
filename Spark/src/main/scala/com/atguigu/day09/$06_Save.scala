package com.atguigu.day09

import java.sql.{Connection, DriverManager, PreparedStatement}

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object $06_Save {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[4]").setAppName("test")
    //设置批次时间 5s一个批次
    val ssc = new StreamingContext(conf,Seconds(5))

    ssc.checkpoint("checkpoint")
    //设置日志级别
    ssc.sparkContext.setLogLevel("error")

    val ds = ssc.socketTextStream("hadoop102",9999)

    ds.flatMap(_.split(" "))
      .map((_,1))
      .reduceByKeyAndWindow((agg:Int,curr:Int)=>agg+curr,Seconds(25),Seconds(10))
        .foreachRDD(rdd=>{
          //jdbc代码保存mysql


          rdd.foreachPartition(it=>{

            var connection:Connection = null
            var statement:PreparedStatement = null

            try{
              connection = DriverManager.getConnection("jdbc:mysql://hadoop102:3306/test","root","root123")
              statement = connection.prepareStatement("insert into wc values(?,?)")
              it.foreach{
                case (wc,num) =>
                  statement.setString(1,wc)
                  statement.setInt(2,num)
                  statement.executeUpdate()
              }
            }catch {
              case e:Exception => e.printStackTrace()
            }finally {
              if(statement!=null)
                statement.close()
              if(connection!=null)
                connection.close()
            }
          })
        })

    ssc.start()
    ssc.awaitTermination()
  }
}

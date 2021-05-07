package com.atguigu.day10

import java.sql.{Connection, PreparedStatement}
import java.text.SimpleDateFormat
import java.util.Date

import org.apache.spark.streaming.dstream.DStream

object $03_Test {


  def run(filterData:DStream[(String,String,String,String,String)]): Unit = {

    val areaCityClickDS = filterData.map{
      case (time,area,city,userid,adid) =>
        val formatter = new SimpleDateFormat("yyyyMMdd")
        val dateStr = formatter.format(new Date(time.toLong))

        (( dateStr,area,city,adid ), 1)
    }.reduceByKey(_+_)
    //2、与mysql统计结果汇总
    areaCityClickDS.foreachRDD(rdd=>{
      rdd.foreachPartition(it=>{

        var connection:Connection = null
        var statement:PreparedStatement = null
        try{
          connection = JdbcUtils.getConnection

          statement = connection.prepareStatement("INSERT INTO area_city_ad_count VALUES(?,?,?,?,?) ON DUPLICATE KEY UPDATE COUNT = COUNT+?")

          it.foreach{
            case (( dateStr,area,city,adid ), num) =>

              JdbcUtils.executeUpdate(Array[Any](dateStr,area,city,adid,num,num),statement)
          }
        }catch {
          case e:Exception => e.printStackTrace()
        }finally {
          if(statement!=null)
            statement.close()
        }
      })
    })
  }
}

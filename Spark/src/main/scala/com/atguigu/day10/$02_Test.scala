package com.atguigu.day10

import java.sql.{Connection, PreparedStatement}
import java.text.SimpleDateFormat
import java.util.Date

import org.apache.spark.streaming.dstream.DStream

object $02_Test {


  def run(filterData:DStream[(String,String,String,String,String)]): Unit ={
    val timeUserAdCount = filterData.map{
      case (time,area,city,userid,adid) =>
        val formatter = new SimpleDateFormat("yyyyMMdd")
        val timeStr = formatter.format(new Date(time.toLong))
        ((timeStr,userid,adid),1)
    }.reduceByKey(_+_)
    //将当前批次统计结果与之前统计结果<点击表>汇总
    timeUserAdCount.foreachRDD(rdd=>{

      rdd.foreachPartition(it=>{
        var connection:Connection = null
        var mergeClickStatement:PreparedStatement = null
        var queryClickTotalStatement:PreparedStatement = null
        var addBlackStatement:PreparedStatement = null
        try{
          connection = JdbcUtils.getConnection
          //更新点击次数sql
          mergeClickStatement = connection.prepareStatement("INSERT INTO user_ad_count VALUES(?,?,?,?) ON DUPLICATE KEY UPDATE COUNT=COUNT+?")
          //查询目前为止点击次数sql
          queryClickTotalStatement = connection.prepareStatement("select count from user_ad_count where dt=? and userid=? and adid=?")
          //添加黑名单sql
          addBlackStatement = connection.prepareStatement("INSERT INTO black_list VALUES(?)")
          it.foreach{
            case ((time,userid,adid),num) => {
              //将当前批次统计结果与之前统计结果<点击表>汇总
              /*              mergeClickStatement.setString(1,time)
                            mergeClickStatement.setString(2,userid)
                            mergeClickStatement.setString(3,adid)
                            mergeClickStatement.setInt(4,num)
                            mergeClickStatement.setInt(5,num)
                            mergeClickStatement.executeUpdate()*/
              JdbcUtils.executeUpdate(Array[Any](time,userid,adid,num,num),mergeClickStatement)
              //查询当前用户对当前广告目前为止点击了多少次
              queryClickTotalStatement.setString(1,time)
              queryClickTotalStatement.setString(2,userid)
              queryClickTotalStatement.setString(3,adid)
              val set = queryClickTotalStatement.executeQuery()
              var totalNum:Int = 0
              while(set.next()){
                totalNum = set.getInt("count")
              }
              //如果超过30次，将用户加入黑名单
              if(totalNum>=30){
                addBlackStatement.setString(1,userid)
                addBlackStatement.executeUpdate()
              }
            }
          }
        }catch {
          case e:Exception => e.printStackTrace()
        }finally {
          if(mergeClickStatement!=null)
            mergeClickStatement.close()
          if(queryClickTotalStatement!=null)
            queryClickTotalStatement.close()
          if(addBlackStatement!=null)
            addBlackStatement.close()
        }
      })
    })
  }
}

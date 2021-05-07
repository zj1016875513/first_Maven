package com.atguigu.day10

import java.text.SimpleDateFormat
import java.util.Date

import org.apache.spark.streaming.Seconds
import org.apache.spark.streaming.dstream.DStream

object $04_Test {

  def run(filterData:DStream[(String,String,String,String,String)]): Unit = {

    val adHmDs = filterData.map{
      case (time,area,city,userid,adid) =>

        val formatter = new SimpleDateFormat("HH:mm")
        val hm = formatter.format(new Date(time.toLong))
        ((adid,hm),1)
    }.reduceByKeyAndWindow( (agg:Int,curr:Int) => agg+curr ,Seconds(120), Seconds(60) )
    //2、按照广告id分组，得到每个广告2分钟内每个时间点的统计次数
    val adGroupedDs = adHmDs.map{
      case ((adid,hm),num) =>
        (adid,(hm,num))
    }.groupByKey()

    adGroupedDs.print()
  }
}

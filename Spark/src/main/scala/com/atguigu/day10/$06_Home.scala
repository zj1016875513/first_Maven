package com.atguigu.day10

import java.text.SimpleDateFormat

import com.atguigu.day06.UserAnalysis
import org.apache.spark.RangePartitioner
import org.apache.spark.sql.SparkSession

object $06_Home {

  def main(args: Array[String]): Unit = {
    val list = List[(String,String,String)](
      ("1001","2020-09-10 10:21:21","home.html"),
      ("1001","2020-09-10 10:28:10","good_list.html"),
      ("1002","2020-09-10 09:40:00","home.html"),
      ("1001","2020-09-10 10:35:05","good_detail.html"),
      ("1002","2020-09-10 09:42:00","favor.html"),
      ("1001","2020-09-10 10:42:55","cart.html"),
      ("1001","2020-09-10 10:43:55","11.html"),
      ("1001","2020-09-10 10:44:55","22.html"),
      ("1001","2020-09-10 10:45:55","33.html"),
      ("1001","2020-09-10 10:46:55","44.html"),
      ("1001","2020-09-10 10:47:55","55.html"),
      ("1001","2020-09-10 10:48:55","66.html"),
      ("1001","2020-09-10 10:49:55","77.html"),
      ("1002","2020-09-10 09:41:00","mine.html"),
      ("1001","2020-09-10 11:35:21","home.html"),
      ("1001","2020-09-10 11:36:10","cart.html"),
      ("1003","2020-09-10 13:10:00","home.html"),
      ("1001","2020-09-10 11:38:12","trade.html"),
      ("1001","2020-09-10 11:39:12","aa.html"),
      ("1001","2020-09-10 11:40:12","bb.html"),
      ("1001","2020-09-10 11:41:12","cc.html"),
      ("1001","2020-09-10 11:42:12","dd.html"),
      ("1001","2020-09-10 11:43:12","ee.html"),
      ("1001","2020-09-10 11:44:12","ff.html"),
      ("1001","2020-09-10 11:45:12","gg.html"),
      ("1001","2020-09-10 11:46:12","hh.html"),
      ("1001","2020-09-10 11:47:12","ll.html"),
      ("1001","2020-09-10 11:38:55","payment.html"),
      ("1003","2020-09-10 13:15:00","search.html")
    )

    val spark = SparkSession.builder().master("local[3]").appName("test").getOrCreate()

    val rdd = spark.sparkContext.parallelize(list)

    val acc = spark.sparkContext.collectionAccumulator[(String,UserAnalysis)]("acc")

    //数据类型转换
    val rdd1 = rdd.map{
      case (userid,dateStr,page) =>
        val formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val time = formatter.parse(dateStr).getTime
        ( s"${userid}_${time}" ,UserAnalysis(userid,time,page))
    }
    //根据用户+时间 范围分区
    //val rdd2 = rdd1.partitionBy(new RangePartitioner(3,rdd1))

  /*  rdd2.mapPartitionsWithIndex((index,it)=>{
      println(s"index:${index}  datas=${it.toList}")
      it
    }).collect()*/
    //按照指定分区器分区，并且在每个分区中排序
    val rdd2 = rdd1.repartitionAndSortWithinPartitions(new RangePartitioner(3,rdd1))

    //修改每个分区中用户的会话与step
    val rdd3 = rdd2.mapPartitions(it=>{

      val slidingList = it.toList
      slidingList.sliding(2).foreach(x=>{
        val first = x.head._2
        val next = x.last._2
        //一个分区中可能有多个用户，所在判断会话的时候需要看看是否是同一个用户
        if( first.userid == next.userid && next.time-first.time<= 30 * 60 * 1000 ){
          next.session = first.session
          next.step = first.step+1
        }
      })
      slidingList.toIterator
    })

    rdd3.mapPartitionsWithIndex( (index,it)=>{

      val list = it.toList
      acc.add( (s"${index}#head",list.head._2) )
      acc.add( (s"${index}#last",list.last._2) )
      list.toIterator
    } ).collect()

    //获取累加器的结果
    val accResult = acc.value

    import scala.collection.JavaConversions._
    val map = accResult.toMap

    //修正累加器的结果
    (1 until map.size/2).foreach(index=>{
      //获取index分区第一条数据
      val first = map.get(s"${index}#head").get
      //获取index分区最后一条数据
      val last = map.get(s"${index}#last").get
      //获取前一个分区的最后一条数据
      val pre = map.get(s"${index-1}#last").get

      //判断当前分区的第一条数据与前一个分区最后一条数据是否是用一个用户，时间是否在30分钟内
      if( pre.userid == first.userid && first.time - pre.time <=30 * 60 * 1000 ){

        //判断当前分区最后一条数据与第一条数据session是否一致,如果一致,需要同步修改最后一条数据session、step
        if( last.session == first.session ){
          last.session = pre.session
          last.step = pre.step + last.step
        }

        //修正当前分区第一条数据
        first.session = pre.session
        first.step = pre.step + 1
      }
    })

    //广播累加器修正结果
    val bc = spark.sparkContext.broadcast(map)

    //利用累加器修正结果修正rdd数据
    rdd3.mapPartitionsWithIndex((index,it)=>{

      val list = it.toList
      //取出广播变量的值
      val bcMap = bc.value

      //取出当前分区修正的第一条和最后一条数据
      val first = bcMap.get(s"${index}#head").get
      val last = bcMap.get(s"${index}#last").get
      //取出当前分区第一条和最后一条数据
      val currentFirst = list.head
      val currentLast = list.last
      val oldSession = currentFirst._2.session
      //判断累加器修正的分区的第一条数据与当前一条数据session是否相同,如果不相同,需要修改当前分区第一条数据的session、step
      if( first.session != oldSession ){
        currentFirst._2.session = first.session
        currentFirst._2.step = first.step
      }

      list.tail.foreach{
        case (key,user) =>
          //修正与第一条数据相同session的所有数据
          if(user.session == oldSession && oldSession!=first.session){
            user.session = first.session
            user.step =first.step + user.step-1
          }
      }


      list.toIterator
    }).mapPartitionsWithIndex((index,it)=>{
      println(s"index:${index}  datas=${it.toList}")
      it
    }).collect()


  }
}

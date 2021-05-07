package com.atguigu.day10

import java.text.SimpleDateFormat
import java.util.UUID

import com.atguigu.day06.UserAnalysis
import org.apache.spark.sql.SparkSession

case class Analysis(userid:String,time:Long,page:String,var session:String = UUID.randomUUID().toString,var step:Int = 1)
object $05_Home {

  //每个用户每次会话<两次之间时间不超过30分钟>的行为轨迹
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

   //1、转换数据类型
    val rdd2 = rdd.map{
      case (userid,datestr,page) =>
        val formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val time = formatter.parse(datestr).getTime
        UserAnalysis(userid,time,page)
    }

    //2、按照用户分组
    val rdd3 = rdd2.groupBy(_.userid)

    //3、对每个用户的数据按照时间进行排序[升序]
    rdd3.flatMap{
      case (userid,userIt) =>

        val userList = userIt.toList.sortBy(_.time)

        val slidingList = userList.sliding(2)

        slidingList.foreach(x => {
          val first = x.head
          val next = x.last
          if( next.time - first.time <= 30 * 60 * 1000){
          //4、对每个用户的数据进行两两比较，修改session、step
            next.session = first.session
            next.step = first.step+1
          }
        } )

        userIt
    }.foreach(println(_))


    //5、结果展示
  }
}

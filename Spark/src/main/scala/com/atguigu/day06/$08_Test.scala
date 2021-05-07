package com.atguigu.day06

import java.text.SimpleDateFormat
import java.util.UUID

import org.apache.spark.{SparkConf, SparkContext}

case class UserAnalysis(userid:String,time:Long,page:String,var session:String = UUID.randomUUID().toString,var step:Int = 1)
object $08_Test {

  def main(args: Array[String]): Unit = {

    val list = List[(String,String,String)](
      ("1001","2020-09-10 10:21:21","home.html"),
      ("1001","2020-09-10 10:28:10","good_list.html"),
      ("1001","2020-09-10 10:35:05","good_detail.html"),
      ("1001","2020-09-10 10:42:55","cart.html"),
      ("1001","2020-09-10 11:35:21","home.html"),
      ("1001","2020-09-10 11:36:10","cart.html"),
      ("1001","2020-09-10 11:38:12","trade.html"),
      ("1001","2020-09-10 11:40:00","payment.html"),
      ("1002","2020-09-10 09:40:00","home.html"),
      ("1002","2020-09-10 09:41:00","mine.html"),
      ("1002","2020-09-10 09:42:00","favor.html"),
      ("1003","2020-09-10 13:10:00","home.html"),
      ("1003","2020-09-10 13:15:00","search.html")
    )
    //分析每个用户每次会话[上一次与下一次时间如果不超过30分钟为一次会话]的行为轨迹

    //1、创建SparkContext
    val sc = new SparkContext(new SparkConf().setMaster("local[4]").setAppName("test"))
    //2、读取数据
    val datas = sc.parallelize(list)
    //3、数据类型转换[转成case class]
    val userRdd = datas.map{
      case (userid,datastr,page)=>
        val formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val time = formatter.parse(datastr).getTime
        UserAnalysis(userid,time,page)
    }
    //4、按照用户分组
    //Iterable
    //Iterator
    val groupRdd = userRdd.groupBy(_.userid)

    //Map(
    //  1001 -> List(
    //        UserAnalysis("1001","2020-09-10 10:21:21","home.html","s0001",1)
    //        UserAnalysis("1001","2020-09-10 10:28:10","good_list.html","s0002",1)
    //        UserAnalysis("1001","2020-09-10 10:35:05","good_detail.html","s0003",1)
    //        UserAnalysis("1001","2020-09-10 10:42:55","cart.html","s0004",1)
    //        UserAnalysis("1001","2020-09-10 11:35:21","home.html","s0005",1)
    //        UserAnalysis("1001","2020-09-10 11:36:10","cart.html","s0006",1)
    //        UserAnalysis("1001","2020-09-10 11:38:12","trade.html","s0007",1)
    //        UserAnalysis("1001","2020-09-10 11:40:00","payment.html","s0007",1)
    //     )
    //   ...
    // )
    //5、对每个用户所有数据按照时间排序
    groupRdd.flatMap(x=>{
      //x = 1001 -> List(
      //      UserAnalysis("1001","2020-09-10 10:21:21","home.html","s0001",1)
      //      UserAnalysis("1001","2020-09-10 10:28:10","good_list.html","s0002",1)
      //      UserAnalysis("1001","2020-09-10 10:35:05","good_detail.html","s0003",1)
      //      UserAnalysis("1001","2020-09-10 10:42:55","cart.html","s0004",1)
      //      UserAnalysis("1001","2020-09-10 11:35:21","home.html","s0005",1)
      //      UserAnalysis("1001","2020-09-10 11:36:10","cart.html","s0006",1)
      //      UserAnalysis("1001","2020-09-10 11:38:12","trade.html","s0007",1)
      //      UserAnalysis("1001","2020-09-10 11:40:00","payment.html","s0007",1)
      //   )
      val slidingList = x._2.toList.sortBy(_.time).sliding(2)
      //List(
      //    List( UserAnalysis("1001","2020-09-10 10:21:21","home.html","s0001",1), UserAnalysis("1001","2020-09-10 10:28:10","good_list.html","s0002",1))
      //    List( UserAnalysis("1001","2020-09-10 10:28:10","good_list.html","s0002",1), UserAnalysis("1001","2020-09-10 10:35:05","good_detail.html","s0003",1))
      //    ....
      //
      // )
      //6、判断上一次与下一次是否位一次会话，赋予会话id与步骤
      slidingList.foreach(z=>{
        //z = List( UserAnalysis("1001","2020-09-10 10:21:21","home.html","s0001",1), UserAnalysis("1001","2020-09-10 10:28:10","good_list.html","s0002",1))
        val first = z.head
        val next = z.last
        if(next.time - first.time <= 30 *60 *1000){
          next.session = first.session
          next.step = first.step+1
        }
      })

      x._2

    })
      .foreach(println(_))


    //7、结果展示
  }
}

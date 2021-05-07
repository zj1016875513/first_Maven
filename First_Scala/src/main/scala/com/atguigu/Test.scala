package com.atguigu

import java.text.SimpleDateFormat

import scala.io.Source

object Test {
  //统计每个用户一小时内的最大登录次数
  //select a.user_id,max(num)
  //	from(
  //		select a.user_id,a.login_time,count(1) num
  //			from user_info a inner join user_info b
  //			on a.user_id = b.user_id
  //			and unix_timestamp(b.login_time) - unix_timestamp(a.login_time) <=3600
  //			and unix_timestamp(b.login_time) >= unix_timestamp(a.login_time)
  //			group by a.user_id,a.login_time
  //	) group by user_id
  def main(args: Array[String]): Unit = {

    //1、读取数据
    val datas = Source.fromFile("First_Scala/datas/1.txt","utf-8").getLines().toList

    //2、切割数据、转换时间字符串为时间戳
    val splitDatas = datas.map(line=>{
      val arr = line.split(",")
      val format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
      val time = format.parse(arr.last).getTime
      (arr.head, time)
    }) //返回每一个用户的每一次登录的时间戳的List
    //2、遍历数据，对每条数据进行过滤
    splitDatas.map(x=>{
      //x = (a,时间戳1)
      val oneTimeRangeDatas = splitDatas.filter(y=> {
        x._1 == y._1 && y._2-x._2<= 3600000 && y._2>=x._2
      })  //用户名相同，时间相差一个小时，y比x时间戳大的情况下的 所有记录

      val num = oneTimeRangeDatas.size   //一个小时内最大的登录次数

      (x._1,x._2,num) //(用户，时间戳，一个小时内最大登录次数)
    })
    //3、统计最大登录次数
      .groupBy(_._1) //根据用户分组  数据变为
      //a->List((a,时间戳,3),(a,时间戳,4),(a,时间戳,5))
      //b->List((b,时间戳,1),(b,时间戳,2),(b,时间戳,3))
      .map(x=>{
        val num = x._2.maxBy(_._3)._3  //List中按每个元组中的num从大到小排序，再取最大num元组的num值  最后将这个最大num赋值给num
        (x._1,num) //(用户名，一个小时内最大登录次数)
      })
    //4、结果展示
      .foreach(println(_))
  }

}

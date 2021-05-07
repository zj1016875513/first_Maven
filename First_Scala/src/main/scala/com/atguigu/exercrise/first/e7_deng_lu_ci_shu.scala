package com.atguigu.exercrise.first

import java.text.SimpleDateFormat
import scala.io.Source


object e7_deng_lu_ci_shu {
  def main(args: Array[String]): Unit = {
    val datas = Source.fromFile("First_Scala/datas/1.txt").getLines().toList
    val splitdatas=datas.map(line=>{
      val arr = line.split(",")
      val user=arr.head
      val formattime= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
      val time=formattime.parse(arr.last).getTime
      (user,time)
    })
    splitdatas.map(x=>{
      val OneHourDatas = splitdatas.filter(y => {
        y._1 == x._1 && y._2-x._2 <= 3600000 && y._2 >= x._2
      })
      val MaxTime=OneHourDatas.size
      (x._1,MaxTime)
    }).groupBy(x=>x._1).map(x=>{
      val num=  x._2.maxBy(_._2)._2
      (x._1,num)
    }).foreach(println(_))
  }
}

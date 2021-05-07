package com.atguigu

import java.text.SimpleDateFormat

    import scala.io.Source

    object Test2 {

      //需求: 统计每个区域平均等客时间
      //1、得到每个司机每次的等客时间和等客区域
      //2、每个区域平均等客时间
      def main(args: Array[String]): Unit = {

        //1、读取数据
        Source.fromFile("First_Scala/datas/2.txt").getLines().toList
        //2、切割、转换时间字符串为时间戳
          .map(line=>{
          val arr = line.split("\t")
          val id = arr.head //司机ID
          val toAddr = arr(2) //落客地点，司机等客时间即位司机到达时间直到下一次上客时间
          val formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
          val fromTime = formatter.parse(arr(3)).getTime //上客时间
          val toTime = formatter.parse(arr.last).getTime //到达时间

          (id,toAddr,fromTime,toTime)
        })
        //3、按照司机分组，对每个司机的数据按照时间排序
          .groupBy(_._1) //根据司机分组
        //Map(
        //  a -> List(
        //         (a,龙岗区,11,20)，
        //         (a,龙华区,30,40)，
        //         (a,宝安区,22,26)
        //       )
        //  b -> ...
        // )
        //4、计算司机每次的等客时间和等客区域
          .toList
          //司机可能在同一个地点上客多次，如果不tolist的话，后面的map会替换前面的map
          //tolist之后 键值对将以集合的方式存在
          .flatMap(x=>{
          //x =     a -> List(
          //              (a,龙岗区,11,20)，
          //              (a,龙华区,30,40)，
          //              (a,宝安区,22,26)
          //            )

          val sortedList = x._2.sortBy(_._3) //按订单开始时间排序
            //生成一个元组list，此list为订单开始时间从小到大排序
          //List(
          // (a,龙岗区,11,20)，
          // (a,宝安区,22,26)，
          // (a,龙华区,30,40)，
          //)
          val slidingList = sortedList.sliding(2)
            //滑窗函数
          //List(List((a,龙岗区,11,20),(a,宝安区,22,26) )  ,List( (a,宝安区,22,26)，(a,龙华区,30,40)))

          val durationList = slidingList.map(z=>{
            //z = List((a,龙岗区,11,20),(a,宝安区,22,26) )
            val first = z.head  //滑窗函数的前一个元组
            val last = z.last //滑窗函数的后一个元组
            val region = first._2  //地区
            val duration = (last._3 - first._4)/1000 //司机等客时间
            (region,duration) //地区，等客时间
          })

          durationList //地区，等客时间
        })

        //5、按照区域分组，统计平均等客时间
          .groupBy(_._1)//根据地区分组
        //Map(
        //    龙岗区 -> List( (龙岗区,10)，(龙岗区,20)，(龙岗区,8))
        //    ....
        // )
          .map(x=>{
          //x = 龙岗区 -> List( (龙岗区,10)，(龙岗区,20)，(龙岗区,8))
          val timeSum = x._2.map(_._2).sum //求出同一个地区的总等待时间
          val count = x._2.size //求出同一个地区的总等待次数
          (x._1,timeSum.toDouble/count) //(地区，总等待时间/总等待次数（即平均等待时间）)
        })
        //6、结果展示
          .foreach(println(_))
      }
}

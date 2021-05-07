package com.atguigu.day06

import org.apache.spark.{SparkConf, SparkContext}

object $07_Test {

  def main(args: Array[String]): Unit = {

    val list = List(1, 2, 3, 4, 5, 6, 7)
    //知道单跳
    val singleList = list.init.zip(list.tail)

    //创建SparkContext
    val sc = new SparkContext(new SparkConf().setMaster("local[4]").setAppName("TEST"))
    //读取数据
    val datas = sc.textFile("datas/user_visit_action.txt")
    //列裁剪[ sessionid、页面id、时间 ]
    val mapRdd = datas.map(line=>{
      val arr = line.split("_")
      (arr(2),arr(3),arr(4))
    })
      //过滤不需要统计的页面
      .filter(x=> list.contains(x._2.toInt))
    //统计每个页面跳转次数[分母]
    val fmRdd = mapRdd.map(x=>(x._2,1))
      .reduceByKey(_+_)

    val fmList = fmRdd.collect().toMap
    println(fmList)
    //统计从a跳到b页面的次数[分子]
    //按照session分组
    val groupedRdd = mapRdd.groupBy(_._1)

    val flatMapRdd = groupedRdd.flatMap(x=>{

      //按照页面访问时间排序
      val sortedList = x._2.toList.sortBy(_._3)
      val slidingList = sortedList.sliding(2)
      //[ [(session1,页面1,时间1)，(session1,页面2,时间2)]  ，  [(session1,页面2,时间2)，(session1,页面3,时间3)] ]
      val result = slidingList.map(z=>{
        //z = [(session1,页面1,时间1)，(session1,页面2,时间2)]
        val fromPage = z.head
        val toPage = z.last
        ((fromPage._2,toPage._2),1)
      })
      result
    })

    val fzRdd = flatMapRdd.reduceByKey(_+_)

    val fzList  = fzRdd.collect().toMap
    //计算跳转 [分子/分母]

    singleList.foreach{
      case (fromPage,toPage) =>
        val fz = fzList.getOrElse((fromPage.toString, toPage.toString), 0)
        val fm = fmList.getOrElse(fromPage.toString,0)
        println(s"从${fromPage}跳转到${toPage}的转化率=${(fz.toDouble)/fm}")
    }
  }
}

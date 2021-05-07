package com.atguigu.day04

import org.apache.spark.{SparkConf, SparkContext}

object Test {

  //统计每个省份点击次数最多的三个广告
  def main(args: Array[String]): Unit = {

    //1、创建SparkContext
    val sc = new SparkContext(new SparkConf().setMaster("local[4]").setAppName("test"))
    //2、读取数据
    val rdd = sc.textFile("Spark/datas/agent.log")
    //3、[过滤、去重、] 列裁剪
    val rdd2 = rdd.map(x=>{
      val arr = x.split(" ")
      val province = arr(1)
      val adid = arr.last
      ((province,adid),1)
    })
    //4、分组聚合、排序取前三
    //按照省份、广告统计次数,得到每个省份每个广告点击总次数
    val rdd3 = rdd2.reduceByKey(_+_)

    //按照省份分组，对每个省份所有广告数据排序取前三
    val rdd4 = rdd3.groupBy{
      case ((province,adid),num) => province   //根据province分组，将province设为key
    }
    //rdd4=List(省份1->(((省份，广告1)，num)，((省份，广告2)，num))
//    println(rdd4.collect().toList)

    //Map(
    //   省份1 -> List( ((省份1,广告1),10)  ， ((省份1,广告2),20)...)
    //   省份2 -> List( ((省份2,广告3),10)  ， ((省份2,广告4),20)...)
    // )
    val rdd5 = rdd4.map{
      case (province,it) =>
        //对广告排序
      val top3 = it.toList.sortBy(_._2).reverse.take(3).map{
        case ((provinces,adid),num) => (adid,num)  //按广告数量排名，取前3个List(广告id，数量)
      }
        (province,top3)
    }

    //5、结果展示
    rdd5.collect().foreach(println(_))
  }
}

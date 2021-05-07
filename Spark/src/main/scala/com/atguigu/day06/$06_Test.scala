package com.atguigu.day06

import org.apache.spark.{SparkConf, SparkContext}

object $06_Test {

  def main(args: Array[String]): Unit = {
    println("--------------------------获取top10热门品类------------------------------")
    //1、创建SparkContext
    val sc = new SparkContext(new SparkConf().setMaster("local[4]").setAppName("test"))

    val acc = new Top10Accumulator

    sc.register(acc, "acc")
    //2、读取数据
    val datas = sc.textFile("datas/user_visit_action.txt")

    //3、切割、转换[ (品类id,(点击次数，下单次数，支付次数))]
    val selectRdd = datas.flatMap(line => {
      val arr = line.split("_")
      val clickid = arr(6)
      val orderids = arr(8)
      val payids = arr(10)
      //点击行为
      if (clickid != "-1") {
        (clickid, (1, 0, 0)) :: Nil
        //下单行为
      } else if (orderids != "null") {
        orderids.split(",").map((_, (0, 1, 0)))
      } else {
        //支付行为
        payids.split(",").map((_, (0, 0, 1)))
      }
    })

    selectRdd.foreach(x => acc.add(x))

    //获取累加器结果
    val result = acc.value

    //前十的热门品类id
    val resultList = result.sortBy {
      case (id, (clickNum, orderNum, payNum)) => (clickNum, orderNum, payNum)
    }.reverse.take(10).map(_._1)

    println("--------------------------获取top10热门品类中每个品类的top10session---------------------------")

    //广播前十热门品类
    val bc = sc.broadcast(resultList)
    //过滤掉不是top10热门品类的数据
    val filterRdd = datas.filter(line=>{
      val clickid = line.split("_")(6)
      bc.value.contains(clickid)
    })
    //按照品类+sesession分组聚合，得到每个热门品类每个session的次数
    val sessionRdd = filterRdd.map(line=>{
      val arr = line.split("_")
      ((arr(6),arr(2)),1)
    }).reduceByKey(_+_)

    //按照品类分组，在每个品类中对所有session排序获取前十
    val groupedSessionRdd = sessionRdd.groupBy(_._1._1)

    groupedSessionRdd.map(x=>{
      val top10session = x._2.toList.sortBy(_._2).reverse.take(10).map(y=>(y._1._2,y._2))
      (x._1,top10session)
    })

    //结果展示
      .foreach(println(_))

  }
}

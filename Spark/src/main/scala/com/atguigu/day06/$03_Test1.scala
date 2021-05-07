package com.atguigu.day06

import org.apache.spark.{SparkConf, SparkContext}

object $03_Test1 {

  def main(args: Array[String]): Unit = {

    //1、创建SparkContext
    val sc = new SparkContext(new SparkConf().setMaster("local[4]").setAppName("test"))
    //2、读取数据
    val datas = sc.textFile("datas/user_visit_action.txt")
    //3、列裁剪
    val selectRdd = datas.map(line=>{
      val arr = line.split("_")
      val clickid = arr(6)
      val oderedids = arr(8)
      val payids = arr(10)
      (clickid,oderedids,payids)
    })
    //4、过滤出点击数据、统计每个品类点击数
    val clickRdd = selectRdd.filter{
      case (clickid,oderedids,payids) => clickid!="-1"
    }

    val clickMapRdd = clickRdd.map{
      case (clickid,oderedids,payids) => (clickid,1)
    }

    val clickRddResult = clickMapRdd.reduceByKey(_+_)
    //5、过滤出下单数据、统计每个品类下单数
    val orderRdd = selectRdd.filter{
      case (clickid,oderedids,payids) => oderedids!="null"
    }
    val orderRddMap = orderRdd.flatMap{
      case (clickid,oderedids,payids) => oderedids.split(",").map((_,1))
    }

    val orderRddResult = orderRddMap.reduceByKey(_+_)
    //6、过滤出支付数据、统计每个品类支付数
    val payRdd = selectRdd.filter{
      case (clickid,oderedids,payids) => payids!="null"
    }

    val payRddMap = payRdd.flatMap{
      case (clickid,oderedids,payids) => payids.split(",").map((_,1))
    }

    val payResultRdd = payRddMap.reduceByKey(_+_)
    //7、join、得到每个品类点击数、下单数、支付数
    val clickAndOrderRdd = clickRddResult.leftOuterJoin(orderRddResult)

    val clickAndOrderPayRdd = clickAndOrderRdd.leftOuterJoin(payResultRdd)

    val numRdd =  clickAndOrderPayRdd.map{
      case (id,( (clickNum,orderNum),payNum )) => (id,clickNum,orderNum.getOrElse(0),payNum.getOrElse(0))
    }
    //8、排序，取前十
    numRdd.sortBy(x=> (x._2,x._3,x._4),false).take(10)

    //9、结果展示
      .foreach(println(_))

    Thread.sleep(100000)
  }
}

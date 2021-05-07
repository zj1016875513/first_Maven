package com.atguigu.day06

import org.apache.spark.{SparkConf, SparkContext}

object $05_Test3 {

  def main(args: Array[String]): Unit = {
    //1、创建SparkContext
    val sc = new SparkContext(new SparkConf().setMaster("local[4]").setAppName("test"))

    val acc = new Top10Accumulator

    sc.register(acc,"acc")
    //2、读取数据
    val datas = sc.textFile("datas/user_visit_action.txt")

    //3、切割、转换[ (品类id,(点击次数，下单次数，支付次数))]
    val selectRdd = datas.flatMap(line=>{
      val arr = line.split("_")
      val clickid = arr(6)
      val orderids = arr(8)
      val payids = arr(10)
      //点击行为
      if(clickid!="-1"){
        (clickid,(1,0,0)) :: Nil
        //下单行为
      }else if(orderids!="null"){
        orderids.split(",").map((_,(0,1,0)))
      }else{
        //支付行为
        payids.split(",").map((_,(0,0,1)))
      }
    })

    selectRdd.foreach(x=>acc.add(x))

    //获取累加器结果
    val result = acc.value

    result.sortBy{
      case (id,(clickNum,orderNum,payNum)) => (clickNum,orderNum,payNum)
    }.reverse.take(10)
      .foreach(println(_))

    Thread.sleep(100000)

  }
}

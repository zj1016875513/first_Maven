package com.atguigu.day06

import org.apache.spark.{SparkConf, SparkContext}

object $04_Test2 {

  def main(args: Array[String]): Unit = {

    //1、创建SparkContext
    val sc = new SparkContext(new SparkConf().setMaster("local[4]").setAppName("test"))
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
    //4、分组聚合
    val numRdd = selectRdd.reduceByKey((agg,curr)=>{
      (agg._1+curr._1,agg._2+curr._2,agg._3+curr._3)
    })

    //5、排序取前十
    numRdd.sortBy({
      case (id,(clickNum,orderNum,payNum)) => (clickNum,orderNum,payNum)
    },false).take(10)
    //6、结果展示
      .foreach(println(_))
  }
}

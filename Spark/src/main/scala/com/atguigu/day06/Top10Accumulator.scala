package com.atguigu.day06

import org.apache.spark.util.AccumulatorV2

import scala.collection.mutable.ListBuffer

class Top10Accumulator extends AccumulatorV2[(String,(Int,Int,Int)), ListBuffer[(String,(Int,Int,Int))]]{

  var list = ListBuffer[(String,(Int,Int,Int))]()
  //判断累加器是否为空
  override def isZero: Boolean = list.isEmpty
  //复制一个累加器
  override def copy(): AccumulatorV2[(String, (Int, Int, Int)), ListBuffer[(String, (Int, Int, Int))]] = new Top10Accumulator
  //重置累加器
  override def reset(): Unit = list.clear()
  //累加元素<每个task中调用，我们调用>
  override def add(v: (String, (Int, Int, Int))): Unit = {
    //list= ListBuffer()
    // v = (12,(1,0,0))
    // v = (12,(0,1,0))
    // v = (13,(0,0,1))
    // v= (12,(1,0,0))
    list.+=(v)
    //list = ListBuffer( (12,(1,0,0)) )
    //list = ListBuffer( (12,(1,0,0)) ,(12,(0,1,0)) )
    //list = ListBuffer( 12-> (1,1,0), (13,(0,0,1)))
    //list = ListBuffer( 12-> (1,1,0),  (13,(0,0,1)), (12,(1,0,0)))

    val grouped = list.groupBy(_._1)
    // Map(
    //    12 -> List( (12,(1,0,0)) ,(12,(0,1,0)) )
    // )



     val result = grouped.map(x=>{
      //x = (品类id1,List( (品类id1,(点击次数,下单次数,支付次数)) ,(品类id1,(点击次数,下单次数,支付次数))))
       //x = 12 -> List( (12,(1,0,0)) ,(12,(0,1,0)) )
        val num = x._2.map(_._2).reduce((agg,curr)=>(agg._1+curr._1,agg._2+curr._2,agg._3+curr._3))
      (x._1, num)


    })
    //result = Map( 12-> (1,1,0))
    // result = Map( 12-> (1,1,0),  (13,(0,0,1)))
    //result = Map (12->(2,1,0), 13->(0,0,1))
    list = ( ListBuffer[(String,(Int,Int,Int))]() .++ (result.toList)  )
  }
  //合并每个task统计结果<driver中调用，Spark调用>
  override def merge(other: AccumulatorV2[(String, (Int, Int, Int)), ListBuffer[(String, (Int, Int, Int))]]): Unit = {

    list.++=(other.value)

    val grouped = list.groupBy(_._1)

    val result = grouped.map(x=>{
      //x = (品类id1,List( (品类id1,(点击次数,下单次数,支付次数)) ,(品类id1,(点击次数,下单次数,支付次数))))
      val num = x._2.map(_._2).reduce((agg,curr)=>(agg._1+curr._1,agg._2+curr._2,agg._3+curr._3))
      (x._1, num)


    })
    list = ( ListBuffer[(String,(Int,Int,Int))]() .++(result.toList)  )
  }
  //得到最终结果
  override def value: ListBuffer[(String, (Int, Int, Int))] = list
}

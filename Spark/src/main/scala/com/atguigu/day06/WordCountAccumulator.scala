package com.atguigu.day06

import org.apache.spark.util.AccumulatorV2

import scala.collection.mutable
//AccumulatorV2[IN,OUT]
//  IN: 累加器累加的元素的类型
//  OUT: 累加器最终结果类型
class WordCountAccumulator extends AccumulatorV2[(String,Int),mutable.Map[String,Int]]{
  var map = mutable.Map[String,Int]()
  //判断累加器是否为空
  override def isZero: Boolean = map.isEmpty
  //复制一个累加器
  override def copy(): AccumulatorV2[(String, Int), mutable.Map[String, Int]] = {
    new WordCountAccumulator
  }
  //重置累加器
  override def reset(): Unit = map.clear()
  //累加元素<在每个task中累加>
  override def add(v: (String, Int)): Unit = {
    println(s"当前task:${Thread.currentThread().getName} 当前累加元素:${v}  当前task之前累加结果：${map}")
    //判断单词是否在累加器中存在，如果存在取出单词之前累加结果+当前次数
    if( map.contains(v._1)){
      val num = map.get(v._1).get
      map.put(v._1,num+v._2)
    }
    //如果不存在，则直接将当前单词和次数添加到累加器中
    else{
      map.put(v._1,v._2)
    }
  }
  //合并所有task的累加结果<在Driver中合并>
  override def merge(other: AccumulatorV2[(String, Int), mutable.Map[String, Int]]): Unit = {

    println(s"当前task:${Thread.currentThread().getName} 传入task累加结果:${other.value}  当前累加结果：${map}")
    //取出传入task累加结果
    //otherMap = [Hello->3,spark->5]
    val otherMap = other.value
    // map = [Hello->5,python->10]
    val totalList = otherMap.toList ::: map.toList
    // totalList = [Hello->3,spark->5,Hello->5,python->10]
    val groupedMap = totalList.groupBy(_._1)
    // Map(
    //    Hello -> List ( Hello->3,Hello->5 )
    //    spark -> List ( spark->5)
    //    python -> List ( python->10)
    // )
    val result = groupedMap.map(x=>{
      //x = Hello -> List ( Hello->3,Hello->5 )
      val sum = x._2.map(_._2).sum
      (x._1,sum)
    })

    map.++=(result)

  }
  //获取累加器的结果
  override def value: mutable.Map[String, Int] = {
    map
  }
}

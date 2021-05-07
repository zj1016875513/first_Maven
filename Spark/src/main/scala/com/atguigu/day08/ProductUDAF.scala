package com.atguigu.day08

import org.apache.spark.sql.{Encoder, Encoders}
import org.apache.spark.sql.expressions.Aggregator

import scala.collection.mutable

case class ProductBuff(val cityInfo:mutable.Map[String,Int],var count:Int)
class ProductUDAF extends Aggregator[String,ProductBuff,String]{
  //赋予中间变量初始值
  override def zero: ProductBuff = ProductBuff(mutable.Map[String,Int](),0)

  /**
    * 在每个task中进行统计更新中间变量
    */
  override def reduce(buff: ProductBuff, city: String): ProductBuff = {

    if(buff.cityInfo.contains(city)){
      buff.cityInfo.put(city, buff.cityInfo.get(city).get+1)
    }else{
      buff.cityInfo.put(city,1)
    }

    buff.count = buff.count+1

    buff
  }

  /**
    * 合并task结果
    * @param b1
    * @param b2
    * @return
    */
  override def merge(b1: ProductBuff, b2: ProductBuff): ProductBuff = {
    //合并
    val list = b1.cityInfo.toList ++ b2.cityInfo.toList

    val groupedMap = list.groupBy(_._1)

    val cityResult = groupedMap.map(x=>{
      (x._1,x._2.map(_._2).sum)
    })

    val totalCount = b1.count + b2.count

    ProductBuff( mutable.Map[String,Int]().++=(cityResult),totalCount)
  }
  //获取最终结果
  override def finish(reduction: ProductBuff): String = {
    val sortedList = reduction.cityInfo.toList.sortBy(_._2).reverse
    val top2 = sortedList.take(2)
    val top2Str = top2.map{
      case (city,num) => s"${city}:${(num.toDouble/reduction.count * 100)}%"
    }.mkString(",")
    //List
    s"${top2Str},其他:${sortedList.drop(2).map(_._2).sum.toDouble/reduction.count *100}%"
  }

  override def bufferEncoder: Encoder[ProductBuff] = Encoders.product[ProductBuff]

  override def outputEncoder: Encoder[String] = Encoders.STRING
}

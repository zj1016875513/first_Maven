package com.atguigu.day07

import org.apache.spark.sql.{Encoder, Encoders}
import org.apache.spark.sql.expressions.Aggregator

/**
  * 强类型UDAF
  *   IN: 输入参数类型
  *   BUF: 中间结果类型
  *   OUT: 最终输出类型
  */
case class Buff(var sum:Int,var count:Int)

class MyAvg extends Aggregator[Int,Buff,Double]{
  //给中间结果赋予初始值
  override def zero: Buff = Buff(0,0)
  //在每个task中更新中间结果
  override def reduce(buff: Buff, age: Int): Buff = {
    //更新sum
    buff.sum = buff.sum + age
    //更新count
    buff.count = buff.count + 1

    buff
  }

  /**
    * 合并所有task的结果
    * @param b1
    * @param b2
    * @return
    */
  override def merge(b1: Buff, b2: Buff): Buff = {
    b1.sum = b1.sum + b2.sum
    b1.count = b1.count +b2.count

    b1
  }
  //获取最终结果
  override def finish(reduction: Buff): Double = reduction.sum.toDouble/reduction.count
  //对中间结果类型编码
  override def bufferEncoder: Encoder[Buff] = Encoders.product[Buff]
  //对结果类型编码
  override def outputEncoder: Encoder[Double] = Encoders.scalaDouble
}

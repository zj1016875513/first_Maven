package com.atguigu.chapter04

object $03_ForTwoMethod {

  /**
    *  for两个重要方法:
    *       to方法:  start.to(end) 生成的集合是左右闭合的集合
    *       until方法: start.to(end) 生成的集合是左闭右开的集合
    */
  def main(args: Array[String]): Unit = {

    val indexs = (1.to(10)).toList
    val indexs2 = (1 to 10).toList
    val indexs3 = (1.until(10)).toList
    val indexs4 = (1 until 10).toList

    val indexs5 = 1.to(10,2)
    1 to 10 by 2

    println(indexs)
    println(indexs2)
    println(indexs3)
    println(indexs4)
    println(indexs5.toList)
   //println(indexs6.toList)
  }
}

package com.atguigu.chapter08

import scala.io.StdIn

object $09_MatchPartil {

  /**
    * 偏函数: 没有match关键字的模式匹配称之为偏函数
    *     语法: val 函数名:PartialFunction[IN,OUT] = {
    *         case 条件 => ...
    *         case 条件 => ...
    *         case 条件 => ...
    *         case 条件 => ...
    *
    *     }
    *     IN: 函数输入类型
    *     OUT: 函数返回值数据类型
    * @param args
    */
  def main(args: Array[String]): Unit = {

    val word = StdIn.readLine("请输入一个单词:")

    word match {
      case "hadoop" =>
        println("hadoop....")
        10
      case "spark"=>
        println("spark....")
        20
      case _ =>
        println("其他..")
        30
    }

    val func:PartialFunction[String,Int] = {
      case "hadoop" =>
        println("hadoop....")
        10
      case "spark"=>
        println("spark....")
        20
      case _ =>
        println("其他....")
        30
    }

    func(word)


    val regions = List(
      ("宝安区",("宝安中学",("王者峡谷班",("蔡文姬",18)))),
      ("宝安区",("宝安中学",("王者峡谷班",("王昭君",18)))),
      ("宝安区",("宝安中学",("王者峡谷班",("牛魔",18)))),
      ("宝安区",("宝安中学",("王者峡谷班",("夏侯",18))))
    )
    val func2:PartialFunction[(String,(String,(String,(String,Int)))),Unit] = {
      case (regionName,(schoolName,(className,(stuName,age)))) =>
        println(stuName)
    }

    regions.foreach(func2)
    //模式匹配+元组的正确使用姿势
    regions.foreach {
      case (regionName,(schoolName,(className,(stuName,age)))) =>
        println(stuName)
    }

  }
}

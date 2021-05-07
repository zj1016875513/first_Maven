package com.atguigu.chapter08

import scala.util.Random

object $03_MatchType {

  def main(args: Array[String]): Unit = {

    val list = List("spark",10,2.0,false,"hello")

    val index = Random.nextInt(list.size)

    val element = list(index)
    println(element)

    val Xxx = "hello"

    element match{
      case Xxx =>
        println("当前元素是hello")
      case x:String =>
        println("当前是字符串")
      case x:Double =>
        println("当前是Double")
      case x:Boolean =>
        println("当前是Boolean")
      case x:Int =>
        println("当前是Int")


      case _ =>
        println("其他类型")
    }
  }
}

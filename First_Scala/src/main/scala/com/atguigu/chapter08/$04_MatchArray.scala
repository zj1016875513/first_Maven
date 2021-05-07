package com.atguigu.chapter08

import scala.collection.immutable

object $04_MatchArray {

  def main(args: Array[String]): Unit = {

    val arr: Array[Any] = Array("hello","haha","aaa")

    arr match{
      case Array(x:Int) =>
        println("匹配数组只有一个元素")
      case Array(x,_*) =>
        println("匹配数组至少有一个元素")
      case Array(x,_,z) =>
      println("匹配数组只有三个元素")

      case _ =>
        println("其他情况。。。")
    }

    arr match{
/*      case x:Array[Int] =>
        println("Array[Int]....")*/
      case x:Array[Any] =>
        println("Array[Any].....")
    }


  }
}

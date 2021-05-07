package com.atguigu.chapter04

object $01_Block {

  /**
    * 块表达式: 有{ }包裹的一块代码称之为块表达式，块表达式的结果值为{}中最后一个表达式的结果值
    * @param args
    */
  def main(args: Array[String]): Unit = {

    val b = {
      println(".........")
      val a = 10
      val c = 20
      a+c
    }

    println(b)

  }
}

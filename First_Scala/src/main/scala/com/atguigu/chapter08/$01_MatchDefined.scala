package com.atguigu.chapter08

import scala.io.StdIn

object $01_MatchDefined {

  /**
    * 模式匹配语法:
    *     变量 match {
    *         case 条件 => ....
    *         case 条件 => ...
    *         ...
    *     }
    * 模式匹配有返回值,返回值为符合条件的分支的块表达式的结果值
    * @param args
    */
  def main(args: Array[String]): Unit = {

    val word = StdIn.readLine("请输入一个单词:")

    println(word)


    val result = word match {
      case "hadoop" =>
        println("输入是hadoop")
        10

      case "spark" =>
        println("输入是spark")
        20

      case "flume" =>
        println("输入的是flume")
        30

        //默认情况
      case x => {
        println(s"其他 ${x}")
        40
      }
        //如果变量不需要在=>右边使用可以用_代替

        //以下情况可能永远也匹配不到，因为40的位置已经匹配了
      case _ =>
        println(s"其他")
        50

    }

    println("返回值："+result)

  }
}

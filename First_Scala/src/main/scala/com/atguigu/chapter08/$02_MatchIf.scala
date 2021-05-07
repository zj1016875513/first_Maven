package com.atguigu.chapter08

import scala.io.StdIn

object $02_MatchIf {

  /**
    * 模式匹配守卫语法:
    *   变量 match{
    *      case 条件 if(布尔表达式) => ...
    *      case 条件 if(布尔表达式) => ...
    *      case 条件 if(布尔表达式) => ...
    *      case 条件 if(布尔表达式) => ...
    *   }
    */
  def main(args: Array[String]): Unit = {

    val line = StdIn.readLine("请输入一个句子:")

    println(line)

    line match {
      case x if(x.contains("hadoop")) =>
        println("句子中有hadooop")
      case x if(x.contains("flume")) =>
        println("句子中有flume")
      case x if(x.contains("spark")) =>
        println("句子中有spark")
      case x =>
        println("其他....")
    }

  }
}

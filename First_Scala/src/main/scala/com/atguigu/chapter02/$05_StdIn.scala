package com.atguigu.chapter02

import scala.io.{Source, StdIn}

object $05_StdIn {

  /**
    *  scala键盘输入: StdIn.readLine/readInt/readBoolean
    *  scala从文件读取数据: Source.fromFile(path).getLines
    * @param args
    */
  def main(args: Array[String]): Unit = {

//    val line = StdIn.readLine("请输入一个字符串:")
//    println(line)
//
//    val age = StdIn.readInt()
//    println(age)

    Source.fromFile("D:\\IDEAworkspace\\first_Maven\\First_Scala\\pom.xml","utf-8").getLines().foreach(println(_))
  }
}

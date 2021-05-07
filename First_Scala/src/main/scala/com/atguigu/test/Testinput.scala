package com.atguigu.test

import scala.io.StdIn

object Testinput {
  def main(args: Array[String]): Unit = {
    println("input name:")
    var name=StdIn.readLine()
    println("input age:")
    var age=StdIn.readShort()
    println("input sal:")
    var sal=StdIn.readDouble()
    println(
      s"""
        |name=$name
        |age=$age
        |sal=$sal
        |""".stripMargin)
    var a ={
      30+20
    }
    println(a)
  }
}

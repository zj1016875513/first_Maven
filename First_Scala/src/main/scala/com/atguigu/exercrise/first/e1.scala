package com.atguigu.exercrise.first

object e1 {
  val datas = Array("spark", "hello", "scala", "python")

  def main(args: Array[String]): Unit = {
    println(getlength(datas, func).toList)
    println(getlength1(datas).toList)

  }

  def getlength(a: Array[String], func: String => Int) = {
    for (element <- a) yield {
      element.length
    }
  }

  val func = (str: String) => str.length

  def getlength1(a: Array[String]) = {
    for (element <- a) yield {
      element.length
    }
  }
}

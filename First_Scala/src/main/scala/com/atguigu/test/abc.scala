package com.atguigu.test

object abc {
  def main(args: Array[String]): Unit = {

    val datas = Array(1,4,6,8,10,3,5)
    val fun =(a:Int) => {a%2==0}
    println(filter(datas, fun).toList)

  }

  def filter(abc:Array[Int],func:Int=>Boolean)={

    for (elem <- abc if(func(elem))) yield {
      elem
    }

  }

}

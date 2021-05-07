package com.atguigu.exercrise.first

object e2 {
  val datas = Array(1, 4, 6, 8, 10, 3, 5)

  def main(args: Array[String]): Unit = {
//    println(onlyEven(datas, func).toList)
    println(onlyEven(datas,(x: Int) => x % 2 == 0).toList)
    println(onlyEven(datas,(x) => x % 2 == 0).toList)
    println(onlyEven(datas,_ % 2 == 0).toList)

  }

  def onlyEven(a: Array[Int], func: Int => Boolean) = {
    for (b <- a if (func(b))) yield {
      b
    }
  }

//  val func = (x: Int) => x % 2 == 0
}

package com.atguigu.exercrise.first

object e5 {
  val datas = Array(1, 4, 6, 8, 10, 3, 5)

  def main(args: Array[String]): Unit = {
    println(result(datas, func))
    //简写1直接传递函数值
    println(result(datas, (n1: Int, n2: Int) => n1 + n2))
    //简写2参数类型可以省略
    println(result(datas, (n1, n2) => n1 + n2))
    //简写3使用_替代
    println(result(datas, _ + _))
  }

  def result(a: Array[Int], func: (Int, Int) => Int) = {
    var sum = a(0)
    for (i <- 1 to a.length - 1) {
      sum += a(i)
    }
    sum
  }

  var func = (n1: Int, n2: Int) => n1 + n2
}

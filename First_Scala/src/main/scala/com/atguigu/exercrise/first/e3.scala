package com.atguigu.exercrise.first

object e3 {
  val datas = Array("zhangsan 20 2500", "lisi 30 5000", "zhaoliu 25 3500")

  def main(args: Array[String]): Unit = {
    println(getMaxage(datas, func))
    println(getMaxage(datas, (str: String) => str.split(" ")(1).toInt))
    println(getMaxage(datas, (str) => str.split(" ")(1).toInt))
    println(getMaxage(datas, _.split(" ")(1).toInt))

  }

  def getMaxage(a: Array[String], func: String => Int) = {
    var tem = a(0)
    var maxage = func(tem)
    for (b <- a) {
      var b_age = func(b)
      if (maxage < b_age) {
        maxage = b_age
        tem = b
      }
    }
    tem
  }

  var func = (str: String) => str.split(" ")(1).toInt

}

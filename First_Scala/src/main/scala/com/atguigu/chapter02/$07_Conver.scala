package com.atguigu.chapter02

object $07_Conver {

  /**
    * 数字与字符串的转换
    *     数字转字符串:
    *       1、toString
    *       2、插值表达式
    *       3、通过+拼接
    *     字符串转数字: toXXX方法转换
    *     数字和数字的转换:
    *         1、低精度转高精度[自动转换]: Int->Long
    *         2、高精度转低精度[通过toXXX方法]: Long->Int
    *
    */
  def main(args: Array[String]): Unit = {


    val a:Int = 10
    println(a.toString)
    println(s"${a}")
    println(a+"")

    val b = "20"
    println(b.toInt)
    println(b.toDouble)

    val c = "20.0"
    //1.txt
    //println(c.toInt)
    println(c.toDouble)

    val d = 20.2
    println(d.toInt)

    val e:Long = a
    println(e)

    val f:Int = e.toInt
    println(f)
  }
}

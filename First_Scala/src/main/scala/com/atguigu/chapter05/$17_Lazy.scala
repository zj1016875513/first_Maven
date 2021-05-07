package com.atguigu.chapter05

object $17_Lazy {

  /**
    * 惰性求值: lazy val 变量名 = 值
    * 使用lazy标识的变量不会立即初始化,而是等到使用变量的时候才会初始化
    * @param args
    */
  def main(args: Array[String]): Unit = {

    lazy val b = {

      println("............")
      10
    }

    println("++++++++++++++++++")

    println(b)

  }
}

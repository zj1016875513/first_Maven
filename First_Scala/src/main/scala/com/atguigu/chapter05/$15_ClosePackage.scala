package com.atguigu.chapter05

object $15_ClosePackage {

  /**
    * 闭包: 使用了外部作用域变量的函数称之为闭包
    * @param args
    */
  def main(args: Array[String]): Unit = {

    println(func(10))
  }
  val y = 20

  val func = (x:Int) => {
    x+y
  }
}

package com.atguigu.day05

object $01_ClosePackage {

  //闭包: 函数体中使用了外部作用域的变量，称为闭包
  def main(args: Array[String]): Unit = {

    val y = 10
    //闭包函数
    val func = (x:Int)=> {
      x+y
    }

    println(func(20))
  }
}

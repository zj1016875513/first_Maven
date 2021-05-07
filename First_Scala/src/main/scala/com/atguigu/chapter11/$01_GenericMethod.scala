package com.atguigu.chapter11

object $01_GenericMethod {

  /**
    * 泛型方法定义语法: def 方法名[T,U,O](参数名: T,参数名:O):U = {....}
    * @param args
    */
  def main(args: Array[String]): Unit = {

    m1[String, Int]("lisi", 20)
  }

  def m1[T,U](x:T,y:U) = {
    println(x,y)
  }
}

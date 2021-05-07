package com.atguigu.chapter05

object $05_FunctionAndMethod {

  /**
    * 函数与方法的区别:
    *     1、存储位置不同:
    *         函数是对象存储在堆里面
    *         方法放在方法区中
    *     2、方法如果定义在class/object可以重载,但是函数不可以
    *         方法定义在方法里面其实就是函数,不可以重载
    *     3、方法可以转换成函数: 方法名 _
    *
    */
  def main(args: Array[String]): Unit = {
    def add2(x:Int,y:Int) = x+y
    //def add2(x:Int) = x
    val func2 = add2 _
  }

  val func = (x:Int,y:Int) => x+y
  //val func = (x:Int) => x

  def add(x:Int,y:Int) = x+y

  def add(x:Int) = x
}

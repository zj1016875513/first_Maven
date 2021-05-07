package com.atguigu.chapter05

//import scala.swing.event.Key.A

object $16_Recurrence {

  /**
    * 递归: 自己调用自己
    *   前提:
    *       1、必须要有退出条件
    *       2、必须定义方法的返回值类型
    *   如果函数递归必须定义函数类型
    * @param args
    */
  def main(args: Array[String]): Unit = {

    println(m1(5))

    println(m2(5))
  }
  // 5 * 4* 3 *2 * 1
  def m1(n:Int):Int = {
    if(n==1) 1
    else n * m1(n-1)
  }


//如果函数递归必须定义函数类型  m2:Int=>Int  定义函数类型

  val m2:Int=>Int = (n:Int) => {
    if(n==1) 1
    else n * m2(n-1)
  }
}

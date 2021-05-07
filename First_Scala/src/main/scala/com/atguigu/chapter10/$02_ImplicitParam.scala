package com.atguigu.chapter10

object $02_ImplicitParam {

  /**
    * 隐式参数: 自动给方法传入一个参数值
    *     语法:
    *         1、def 方法名(参数名:类型,...)(implict 参数名:类型) = {....}
    *         2、implict val 参数名: 类型 = 值
    */
  def main(args: Array[String]): Unit = {

//    import com.atguigu.chapter01.XX.a
//    println(add(10, 20))
  }


  def add(x:Int,y:Int)(implicit z:Int) = x+y+z
}

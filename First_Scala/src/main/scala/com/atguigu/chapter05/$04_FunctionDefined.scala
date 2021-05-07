package com.atguigu.chapter05

object $04_FunctionDefined {

  /**
    * 函数定义语法: val 函数名 = (参数名:参数类型,...) => { 函数体 }
    *     函数的返回值就是函数体块表达式的结果值
    *  函数的简化: 如果函数体只有一行语句,{ }可以省略
    *
    * 方法就是函数,函数也是对象
    */
  def main(args: Array[String]): Unit = {

    println(add(10,20))
    println(add(10,20))

    println(func)
  }

  val add = (x:Int,y:Int) => x+y

  val func = new Function2[Int,Int,Int] {
    //函数体执行逻辑
    override def apply(v1: Int, v2: Int): Int = {
      v1+v2
    }
  }

}

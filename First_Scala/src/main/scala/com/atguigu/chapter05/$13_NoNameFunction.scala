package com.atguigu.chapter05

object $13_NoNameFunction {

  /**
    * 匿名函数: 没有函数名的函数称之为匿名函数
    * 匿名函数不能单独使用,一般是作为参数值传递
    */
  def main(args: Array[String]): Unit = {

    val func = (x:Int,y:Int) => x+y
    //(x:Int,y:Int) => x-y
    println(func(20, 30))

    add(10,20,func)
    //一般是作为参数值传递
    add(10,20,(x:Int,y:Int) => x+y)  //匿名函数一般作为参数传递
  }

  def add(x:Int,y:Int,func: (Int,Int)=>Int) = {
    func(x,y)
  }


}

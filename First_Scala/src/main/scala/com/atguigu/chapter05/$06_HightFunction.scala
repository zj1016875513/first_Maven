package com.atguigu.chapter05

object $06_HightFunction {

  /**
    * 高阶函数: 以函数作为方法的参数或者是返回值的方法/函数称之为高阶函数
    *
    */
  def main(args: Array[String]): Unit = {

    val func = (x:Int,y:Int) => x+y
    val result = add(10,20,func)
    println(result)

    println(sum(20, 40, func))
  }

  //高阶函数
  def add(x:Int,y:Int,func: (Int,Int) => Int ) = {
    func(x,y)
  }
  //高阶函数
  val sum = (x:Int,y:Int, func: (Int,Int)=>Int) =>{
    func(x,y)
  }
}

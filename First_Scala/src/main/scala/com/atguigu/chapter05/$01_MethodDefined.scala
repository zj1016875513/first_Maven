package com.atguigu.chapter05

object $01_MethodDefined {

  /**
    * 方法语法: def 方法名( 参数名:类型 ,...): 返回值类型 = {方法体}
    * scala方法可以定义在任何地方。
    */
  def main(args: Array[String]): Unit = {

    def add(x:Int,y:Int):Int = {
      x+y
    }

    val result = add(10,20)
    println(result)
  }



}

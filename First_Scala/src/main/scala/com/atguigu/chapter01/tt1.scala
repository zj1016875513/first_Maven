package com.atguigu.chapter01

object tt1 {
  def main(args: Array[String]): Unit = {
    def calculator1(a:Int,b:Int,operator:(Int,Int)=>Int):Int={
      operator(a,b)
    }
    val plus=(x:Int,y:Int)=>{x+y}
    val chen=(x:Int,y:Int)=>{x*y}
    println(calculator1(1, 2, plus))
  }
}

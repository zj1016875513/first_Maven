package com.atguigu.chapter07

object $18_ParCollection {

  def main(args: Array[String]): Unit = {

    val list = List(10,2,4,5,9,100,200,300)

    list.foreach(x=>{
      println(Thread.currentThread().getName)
      println(x)
    })

    val list2 = list.par
    println("*"*100)
    list2.foreach(x=>{
      println(Thread.currentThread().getName)
      println(x)
    })
  }
}

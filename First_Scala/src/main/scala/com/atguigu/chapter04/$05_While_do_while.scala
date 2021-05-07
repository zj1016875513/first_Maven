package com.atguigu.chapter04

object $05_While_do_while {

  /**
    * scala while循环与do-while循环用法与java完全一样
    * while与do-while的区别:
    *   while是先判断后执行
    *   do-while是先执行后判断
    * @param args
    */
  def main(args: Array[String]): Unit = {

    var i = 11
    while(i<=10){
      println(s"i=${i}")
      i = i+1
    }
    println("*"*60)
    var j=11
    do{
      println(s"j=${j}")
      j = j+1
    }while(j<=10)
  }
}

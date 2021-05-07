package com.atguigu.chapter04

import scala.util.control.Breaks._
object $06_Break_Continue {

  /**
    * break: 结束当前循环
    * continue: 结束本次循环，开始下一次循环
    *
    * scala中没有break和continue。
    *
    * scala实现break:
    *   1、导入包: import scala.util.control.Breaks._
    *   2、使用breakable 与 break 方法实现
    * @param args
    */
  def main(args: Array[String]): Unit = {

    //实现break
    try {
      var a = 0
      while (a <= 10) {
        if (a == 5) {
          throw new Exception()
        } else {
          println(s"a=${a}")
        }
        a = a+1
      }
    }catch{
      case e:Exception =>
      }


    println("*"*100)
    //实现continue
    var b = 0
    while (b<=10){
      try{
        if(b==5){
          b=b+1
          throw  new Exception
        }
        else{

          println(s"b=${b}")
          b=b+1
        }

      }catch {
        case e:Exception =>
      }
    }


    //scala实现break
    println("*"*100)
      var c=0
    breakable({
      while (c <= 10) {
        if (c == 5) {
          break()
        } else {
          println(s"c=${c}")
        }
        c= c+1
      }
    })

    //scala实现continue
    println("*"*100)
     b = 0
      while (b<=10){
        breakable({
          if(b==5){
            b=b+1
            break()
          }
          else{
            println(s"b=${b}")
            b=b+1
          }
        })
      }
    //break breakable放在while循环外面
    //continue breakable放在while循环里面
    }
}

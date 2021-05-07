package com.atguigu.test

import scala.io.StdIn

object TestIfElse {
  def main(args: Array[String]): Unit = {

      println("input your age")
      var age = StdIn.readInt()

      var res = if(age > 18){
        "您以成人"
      }else{
        "小孩子一个"
      }

      println("res参数："+res)
    }

}

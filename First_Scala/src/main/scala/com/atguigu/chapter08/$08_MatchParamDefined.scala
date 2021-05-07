package com.atguigu.chapter08

object $08_MatchParamDefined {

  def main(args: Array[String]): Unit = {

    val (name,age,address) = ("zhangsan",20,"shenzhen")

    println(name)

    val Array(y,x,_*) = Array(1,2,3)
   /* val arr = Array(1,2,3)
    arr match{
      case Array(x) => println("...")
    }*/
    println(x)

    val a :: Nil = List(1)

    val map = Map("aa"->10,"bb"->20)

    for((key,value)<- map){
      println(key+"->"+value)
    }

  }
}

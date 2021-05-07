package com.atguigu.chapter07

import scala.collection.mutable

object $09_MutableMap {

  /**
    *  创建方式:
    *     1、mutable.Map[K的类型,V的类型]( K->V,K->V,..)
    *     2、mutable.Map[K的类型,V的类型]( (K,V),(K,V),..)
    */
  def main(args: Array[String]): Unit = {

    val map1 = mutable.Map[String,Int]("aa"->10,"lisi"->30,"cc"->40)
    println(map1)
    println(map1.get("aa"))
    println(map1.get("aa").get)

    val map2  = mutable.Map[String,Int]( ("lisi",30) ,("wangwu",40),("zhaoliu",50) )
    println(map2)

    //添加元素
    val map3 = map2.+( "aa"->30 )
    println(map3)

    map2.+=( ("cc",40) )
    println(map2)

    val map4 = map2.++( List("dd"->10,"ee"->20) )
    println(map4)

    val map5 = map2.++:( List("dd"->10,"ee"->20) )
    println(map5)

    map2.++=(List("hello"->100,"spark"->200))
    println(map2)

    map2.put("scala",300)
    println(map2)
    
    //删除元素
    val map6 = map2.-("cc")
    println(map6)

    map2.-=("cc")
    println(map2)

    val map7 = map2.--(List("zhaoliu","hello"))
    println(map7)

    map2.--=(List("wangwu","lisi"))
    println(map2)

    map2.remove("hello")
    println(map2)

    //获取元素
    //根据key获取value值
    println(map2.getOrElse("spark", 0))

    //获取所有key
    println(map2.keys.toList)

    //获取所有value
    println(map2.values.toList)

    //修改元素
    map2("spark") = 1000
    println(map2)

    map2.update("scala",400)
    println(map2)
  }
}

package com.atguigu.chapter07

object $10_CollectionFields {

  def main(args: Array[String]): Unit = {

    val list = List(10,3,5,6,1)

    //判断集合是否为空
    println(list.isEmpty)

    //获取集合长度
    println(list.length)
    println(list.size)

    //判断集合是否不为空
    println(list.nonEmpty)

    //将集合转成字符串
    println(list.mkString("#"))

    //是否包含
    println(list.contains(100))
  }
}

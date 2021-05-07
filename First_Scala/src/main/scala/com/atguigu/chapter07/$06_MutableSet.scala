package com.atguigu.chapter07

import scala.collection.mutable

object $06_MutableSet {

  def main(args: Array[String]): Unit = {
    //创建方式
    val set = mutable.Set[Int](10,3,2,1,2)
    println(set)

    //添加元素
    val set2 = set.+(30)
    println(set2)

    set.+=(40)
    println(set)

    val set4 = set.++(List(100,300,50))
    println(set4)

    val set5 = set.++:(List(100,300,50))
    println(set5)

    set.++=(List(500,600,700))
    println(set)

    //删除元素
    val set6 = set.-(600)
    println(set6)

    set.-=(700)
    println(set)

    val set7 = set.--(List(1,2,3))
    println(set7)

    set.--=(List(10,40,2,3))
    println(set)

    //修改
    set.update(1000,true)
    println(set)
    set.update(1000,false)
    println(set)
  }
}

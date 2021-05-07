package com.atguigu.chapter07

import scala.collection.mutable.ListBuffer

object $04_MutableList {

  /**
    * 创建方式:
    *     1、ListBuffer[元素类型](初始元素,...)
    *     2、new ListBuffer[元素类型]()
    * @param args
    */
  def main(args: Array[String]): Unit = {

    //创建
    val list = ListBuffer[Int](10,2,5,8)

    val list2 = new ListBuffer[Int]()
    println(list)
    println(list2)

    //添加元素
    val list3 = list.+:(20)
    println(list3)

    val list4 = list.:+(30)
    println(list4)

    list.+=(50)
    println(list)

    list.+=:(60)
    println(list)

    val list5 = list.++(Array(100,200))
    println(list5)

    val list6 = list.++:(Array(300,400))
    println(list6)

    list.++=(List(500,600))
    println(list)

    list.++=:(List(700,800))
    println(list)



    //删除元素
    val list7 = list.-(600)
    println(list7)

    list.-=(700)
    println(list)

    val list8 = list.--(List(10,2,5))
    println(list8)

    list.--=(List(800,500,600))
    println(list)

    list.remove(3,3)
    println(list)
    //插入数据
    list.insert(2,10,20,30)
    println(list)

    //获取元素
    println(list(2))

    //修改元素
    list(2)=100
    println(list)
    list.update(2,400)
    println(list)

    //可变List转不可变List
    val list13 = list.toList

    //不可变转可变
    val list14 = list13.toBuffer

  }
}

package com.atguigu.chapter07

object $05_ImmutableSet {

  /**
    * set集合特点: 无序、不重复
    */
  def main(args: Array[String]): Unit = {
    //创建方式
    val set = Set[Int](10,3,5,1,3)
    println(set)

    //添加元素
    val set2 = set.+(30)
    println(set2)

    val set3 = set.++(List(100,300,20))
    println(set3)

    val set4 = set.++:(List(100,300,20))
    println(set4)

    //删除元素
    val set5 = set.-(10)
    println(set5)

    val set6 = set.--(List(3,1))
    println(set6)


  }
}

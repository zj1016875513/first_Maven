package com.atguigu.chapter07

object $01_ImmutableArray {

  /**
    * 不可变数组创建方式:
    *     1、new Array[元素类型](数组长度)
    *     2、Array[元素类型](初始元素,...)
    *
    * 添加元素
    *
    * 删除元素
    *
    * 修改元素
    *
    * 获取元素
    *
    * 集合中公有方法的区别:
    *     带+与带- : 带+是添加元素,带-是删除元素
    *     一个+/- 与两个+/-的区别:  一个+/-是添加/删除单个元素, 两个+/- 是添加/删除一个集合的所有元素
    *     冒号在前与冒号在后以及不带冒号的区别: 冒号在前是将元素添加到集合末尾,冒号在后是将元素添加到集合最前面,不带冒号是将元素添加到最后面
    *     带=与不带=的区别: 带=的是修改集合本身，不带=是生成一个新集合，原有集合没有改变
    */
  def main(args: Array[String]): Unit = {

    //1、创建方式
    val arr = new Array[Int](10)
    println(arr.toList)

    val arr2 = Array[Int](10,2,5,6,8)
    println(arr2.toList)

    //添加元素
    //添加单个元素
    val arr3 = arr2.+:(20)
    println(arr3.toList)
    println(arr2.toList)

    println(arr2.eq(arr3))

    val arr4 = arr2.:+(39)
    println(arr4.toList)

    //添加多个元素
    val arr5 = arr2.++(Array(100,200,300))
    println(arr5.toList)

    println(arr2.++(Array(100, 20,2,3,4,5,6,7,8)).toList) //这是用arr2集合进行了计算生成了新的匿名数组，arr2并没有改变
    println("arr2数组="+arr2.toList)

    val arr6 = arr2.++:(Array(400,500,600))
    println(arr6.toList)
    println(arr2.toList)

    //获取元素
    println( arr2(0) )

    //修改元素
    arr2(0) = 100
    println(arr2.toList)


    //遍历
    for(element<- arr2){
      println(element)
    }
  }
}

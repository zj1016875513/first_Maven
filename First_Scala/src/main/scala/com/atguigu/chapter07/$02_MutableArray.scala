package com.atguigu.chapter07

import scala.collection.mutable.ArrayBuffer

object $02_MutableArray {

  /**
    * 创建方式:
    *     1、new ArrayBuffer[元素类型]()
    *     2、ArrayBuffer[元素类型](初始元素,...)
    * @param args
    */
  def main(args: Array[String]): Unit = {

    //创建
    val arr = new ArrayBuffer[Int]()

    println(arr)

    val arr2 = ArrayBuffer[Int](10,3,5,7,1)
    println(arr2)

    //添加
    //添加单个元素，不修改集合本身，会生成新集合
    val arr3 = arr2.+:(20)
    println(arr3)
    println(arr2)

    val arr4 = arr2.:+(30)
    println(arr4)

    //添加单个元素,修改集合本身
    arr2.+=(40)
    println(arr2)

    arr2.+=:(50)
    println(arr2)

    //添加一个集合所有元素,不修改集合本身
    val arr5 = arr2.++(Array(100,200,100))
    println(arr5)

    val arr6 = arr2.++:(Array(300,400))
    println(arr6)

    //添加一个集合所有元素，修改集合本身
    arr2.++=(Array(500,600,500))
    println(arr2)

    arr2.++=:(Array(700,800))
    println("*"*100)
    println(arr2)

    //插入数据到指定位置
    arr2.insert(2,10,20,30,40)
    println(arr2)
    //删除元素
    //删除单个元素，不修改集合本身
    val arr7 = arr2.-(1) //括号中是元素自己
    println(arr7)
    println(arr2)
    //删除单个元素，修改集合本身
    arr2.-=(500) //括号中是从前往后的元素
    println(arr2)

    //删除一个集合所有元素，不修改集合本身
    val arr8 = arr2.--(Array(3,5,7)) //元素如果不是连续的也没关系
    println(arr8)

    //删除一个集合所有元素，修改集合本身
    arr2.--=(Array(700,800,600))
    println(arr2)
    println("remove(2,4)之后")
    arr2.remove(2,4)  //从下标2开始往后移除4个元素
    println(arr2)
    //获取元素
    println(arr2(3))

    //修改元素
    arr2(3)=1000
    println(arr2)

    arr2.update(4,2000)
    println(arr2)

    val arr9 = arr2.updated(5,3000)
    println(arr9)

    //遍历
    for(element<- arr2){
      println(element)
    }

    //将可变数组转成不可变数组
    val arr10 = arr2.toArray

    //将不可变数组转成可变数组
    val arr11 = arr10.toBuffer

    //多维数组
    println("多维数组：")
    val arr12 = Array.ofDim[Int](3,4)
    println(arr12.length)
    println(arr12(0).length)
  }
}

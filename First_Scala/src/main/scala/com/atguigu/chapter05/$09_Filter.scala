package com.atguigu.chapter05

object $09_Filter {

  /**
    * 2、根据指定的规则对数组里面的元素进行过滤,只保留符合要求的元素
    * val datas = Array(1,4,6,8,10,3,5)
    * 规则: 只要偶数数据
    * 结果: Array(4,6,8,10)
    *
    * @param args
    */
  def main(args: Array[String]): Unit = {

    val abc = Array(1,4,6,8,10,3,5)

    val func = (x:Int) => x%2!=0

    println(filter(abc, func).toList)
  }

  def filter(xyz:Array[Int], func: Int => Boolean) ={

    for(element<- xyz if(func(element))) yield{
        element
    }
  }
}

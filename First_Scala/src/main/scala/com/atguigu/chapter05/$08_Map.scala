package com.atguigu.chapter05

object $08_Map {

  /**
    * 1、对数组里面的每个元素进行操作,操作规则有外部决定
    * val datas = Array("spark","hello","scala","python")
    * 规则: 获取数组每个元素长度返回
    * 结果: Array(5,5,5,6)
    */
  def main(args: Array[String]): Unit = {

    val abc = Array("spark","hello","scala","python")

    val func = (x:String) => x.charAt(0)
    val func2 = (x:String) => x.length

    println(map(abc, func).toList)
    //直接将函数值传递进去
    println(map(abc, (x:String) => x.length).toList)
    //省略函数参数类型
    println(map(abc, (x) => x.length).toList)
    //函数参数只有一个,()可以省略
    println(map(abc, x => x.length).toList)
    //函数参数只使用了一次,用_代替
    println(map(abc, _.length).toList)
  }

  def map(xyz:Array[String], func: String => Any) = {

    for( element<- xyz) yield {
      func(element)
    }
  }

}

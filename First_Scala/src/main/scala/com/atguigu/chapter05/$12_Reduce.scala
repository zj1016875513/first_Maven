package com.atguigu.chapter05

object $12_Reduce {

  /**
    * 5、根据指定的规则对数组进行聚合
    * val datas = Array(1,4,6,8,10,3,5)
    * 规则: 对数组里面的所有元素求和
    * 结果: 37
    */
  def main(args: Array[String]): Unit = {

    val datas = Array(1,4,6,8,10,3,5)

    val func = (agg:Int,curr:Int) => agg*curr
    println(reduce(datas, func))
    //直接传递函数值
    println(reduce(datas, (agg:Int,curr:Int) => agg*curr))
    //函数类型可以省略
    println(reduce(datas, (agg,curr) => agg*curr))
    //使用_代替
    println(reduce(datas, _*_))
  }

  def reduce(datas:Array[Int], func: (Int,Int) => Int) = {

    var agg = datas(0) //将数组初值赋给agg

    for(index <- 1 until datas.length){  //从数组除开第一位数后进行循环

      agg = func(agg, datas(index))      //将数据进行累加
    }
    agg

  }

}

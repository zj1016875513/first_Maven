package com.atguigu.chapter05

import java.util

object $11_GroupBy {

  /**
    * 4、根据指定的规则对数据进行分组
    * val datas = Array("zhangsan 男 shenzhen","lisi 女 beijing","zhaoliu 男 beijing")
    * 规则: 根据性别分组
    * 结果: Map( 男 -> List("zhangsan 男 shenzhen","zhaoliu 男 beijing") ,女 —> List( "lisi 女 beijing"))
    *
    */
  def main(args: Array[String]): Unit = {

    val datas = Array("zhangsan 男 shenzhen","lisi 女 beijing","zhaoliu 男 beijing")

    val func = (x:String) => x.split(" ")(2)

    println(groupBy(datas, func))
    //直接传递函数值
    println(groupBy(datas, (x:String) => x.split(" ")(2)))
    //函数参数类型省略
    println(groupBy(datas, (x) => x.split(" ")(2)))
    //函数参数个数只有一个,（）省略
    println(groupBy(datas, x => x.split(" ")(2)))
    //函数的参数只在函数体中使用了一次,可以用_代替
    println(groupBy(datas, _.split(" ")(2)))
  }

  def groupBy(datas:Array[String], func: String => String) = {

    val result = new util.HashMap[String,util.List[String]]()

    for(element<- datas){

      val city = func(element)

      if( result.containsKey(city)){

        val list = result.get(city)
        list.add(element)
      }else{
        val list = new util.ArrayList[String]()
        list.add(element)
        result.put(city,list)
      }
    }

    result
  }
}

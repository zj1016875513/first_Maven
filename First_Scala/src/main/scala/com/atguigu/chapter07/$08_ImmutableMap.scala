package com.atguigu.chapter07

object $08_ImmutableMap {

  /**
    * 创建方式:
    *     1、Map[K的类型,V的类型]( (K,V),(K,V),... )
    *     2、Map[K的类型,V的类型]( K->V ,K->V,...)
    * Option主要是为了解决空指针异常,Option是提醒外部当前有可能返回为空，需要外部处理
    *     Option有两个子类:
    *         None: 代表没有值
    *         Some(value): 代表有值,后续可以从Some中获取值
    * @param args
    */
  def main(args: Array[String]): Unit = {

    //创建
    val map1 = Map[String,Int]( ("zhangsan",20),("lisi",40),("wangwu",50) )
    println(map1)

    val map2 = Map[String,Int]( "zhangsan"->20,"lisi"->30,"wangwu"->50 )
    println(map2)

    //添加元素
    val map3 = map2.+( ("zhaoliu",60) )
    println(map3)

    val map4 = map2.++(List( "aa"->10,("bb",20) ))
    println(map4)
    val map5 = map2.++:(List( "aa"->10,("bb",20) ))
    println("map5="+map5)

    //删除元素
    val map6 = map5.-("aa")
    println(map6)

    val map7 = map5.--(List("aa","bb"))
    println(map7)

    //获取数据
    //println(map5.get("lisi").get)
    //根据key获取value值
    println(map5.getOrElse("pp", 0))
    println("map5="+map5)

    //获取所有的key
    for(key<- map5.keys)
      {
        println(s"key=${key}")
      }
    //获取所有的value
    for(value<- map5.values){
      println(s"value=${value}")
    }

    //修改数据
    val map8 = map5.updated("pp",100)
    println(map8)

  }
}

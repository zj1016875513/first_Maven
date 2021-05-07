package com.atguigu.exercrise.first

import java.util

object e4 {
  val datas = Array("zhangsan 男 shenzhen", "lisi 女 beijing", "zhaoliu 男 beijing")
  val map = new util.HashMap[String, util.List[String]]()

  def main(args: Array[String]): Unit = {
    println(getmap(datas, func))
//    println(getmap(datas, (str: String) => str.split(" ")(1)))
//    println(getmap(datas, (str) => str.split(" ")(1)))
//    println(getmap(datas, _.split(" ")(1)))

  }

  def getmap(a: Array[String], func: String => String) = {
    for (b <- a) {
      var key = func(b)
      if (map.containsKey(key)) {
        val list = map.get(key)
        list.add(b)
      } else {
        var list = new util.ArrayList[String]()
        list.add(b)
        map.put(key, list)
      }

    }
    map
  }
  val func = (str: String) => str.split(" ")(1)
}

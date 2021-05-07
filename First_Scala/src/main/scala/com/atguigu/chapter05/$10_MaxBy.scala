package com.atguigu.chapter05

object $10_MaxBy {

  /**
    * 3、根据指定的规则获取数组中最大的元素
    * val datas = Array("zhangsan 20 2500","lisi 30 5000","zhaoliu 25 3500")
    * 规则: 获取年龄最大的人的信息
    * 结果: "lisi 30 5000"
    *
    */
  def main(args: Array[String]): Unit = {
    val datas = Array("zhaoliu 25 3500","zhangsan 20 2500","lisi 30 5000")

    val func = (x:String) => x.split(" ")(2).toInt

    println(maxBy(datas, func))
    //直接传递函数值
    println(maxBy(datas, (x:String) => x.split(" ")(2).toInt))
    //省略函数参数类型
    println(maxBy(datas, (x) => x.split(" ")(2).toInt))
    //用_代替
    println(maxBy(datas, _.split(" ")(2).toInt))
  }

  def maxBy(datas:Array[String],func: String => Int) = {

    //tmp = zhaoliu 25 3500
    //tmpField = 25
    //第一次遍历:
    //  element = "zhaoliu 25 3500"
    //  filed = 25
    //第二次遍历
    //  element = "zhangsan 20 2500"
    //  filed = 20
    //     tmpField = 20
    //     tmp = zhangsan 20 2500
    var tmp = datas(0)

    var tmp_age = func(tmp)
    for(element<- datas){

      val element_age = func(element)

      if(element_age > tmp_age ) {
        tmp_age = element_age
        tmp = element
      }
    }
    tmp
  }
}

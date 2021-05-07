package com.atguigu.chapter07

object $07_Tuple {

  /**
    * 创建方式:
    *     1、(初始元素,初始元素,..)
    *     2、特殊形式[只有二元元组才能使用此种方式创建]:  K -> V
    * 元组中最多只能存储22个元素
    * 元组一旦创建，元组的长度和元素都不可改变
    * 元组获取元素的方式: 元组名._角标 [元组的角标从1开始]
    */
  def main(args: Array[String]): Unit = {

    //创建
    val t1 = ("zhangsan",20,"shenzhen")

    val t2 = "lisi"->30

    println(t1._1)


    val datas = List("zhangsan 20 shenzhen","wangwu 30 beijing")

    val tarr = for(element<- datas) yield {
      val arr = element.split(" ")
      (arr(0),arr(1).toInt,arr(2))
    }

    //元组在工作中一般用来封装比较简单的数据,不适合嵌套的数据
    val region = new Region("宝安区",new School("宝安中学",new Clazz("王者峡谷班",new Student("蔡文姬",18))))

    val regiont = List(
      ("宝安区",("宝安中学",("王者峡谷班",("蔡文姬",18)))),
      ("宝安区",("宝安中学",("王者峡谷班",("王昭君",18)))),
      ("宝安区",("宝安中学",("王者峡谷班",("牛魔",18)))),
      ("宝安区",("宝安中学",("王者峡谷班",("夏侯",18))))
    )

    //获取每个学生的姓名
    for(element<- regiont){

      println(element._2._2._2._1)
    }

  }

  class Region(name:String,school:School)

  class School(name:String,clazz:Clazz)

  class Clazz(name:String,stu:Student)

  class Student(name:String,age:Int)
}

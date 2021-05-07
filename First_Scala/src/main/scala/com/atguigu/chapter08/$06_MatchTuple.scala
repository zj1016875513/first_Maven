package com.atguigu.chapter08

object $06_MatchTuple {

  def main(args: Array[String]): Unit = {

    val t1 = ("zhangsan",20,"shenzhen")
    //元组在匹配的时候变量是几元元组，匹配的值就应该是几元元组
    t1 match {
      case (name,age,address) =>
        println(name,age,address)
      case (name:String,age:Int,address:String) =>
        println(".....")
    }

    val regions = List(
      ("宝安区",("宝安中学",("王者峡谷班",("蔡文姬",18)))),
      ("宝安区",("宝安中学",("王者峡谷班",("王昭君",18)))),
      ("宝安区",("宝安中学",("王者峡谷班",("牛魔",18)))),
      ("宝安区",("宝安中学",("王者峡谷班",("夏侯",18))))
    )

    //
    regions.foreach(x=> {
      x match {
        case (regionName,(schoolName,(className,(stuName,age)))) =>
          println(stuName)
      }
    })
  }
}

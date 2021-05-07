package com.atguigu.chapter06

object $09_TraitDefined {

  trait Log{

    //抽象属性
    val name:String
    //具体属性
    val age:Int = 20
    //抽象方法
    def add(x:Int,y:Int):Int
    //具体方法
    def m2(x:Int) = x*x
  }

  trait Log2

  trait Log3

  class BB

/*  class WarnLog extends Log with Log2 with Log3{

    override val name: String = "xx"

    override def add(x: Int, y: Int): Int = x+y
  }*/

  class WarnLog extends BB with Log2 with Log3 with Log {

    override val name: String = "xx"

    override def add(x: Int, y: Int): Int = x+y
  }

  class ErrorLog
  /**
    * 特质语法: trait 特质名{...}
    * 特质中既可以定义抽象方法也可以定义具体方法，既可以定义抽象属性也可以定义具体属性
    * 特质的继承:
    *     1、子类不需要继承class，此时子类实现第一个trait使用extends关键字，其他的trait的实现通过with关键字
    *     2、子类需要继承class,此时extends关键字用来继承class，特质的实现通过with关键字
    *
    *  特质的混入: 指定让某个对象拥有特质的成员
    *     语法:  new class名称(属性值,..)  with 特质名
    * @param args
    */
  def main(args: Array[String]): Unit = {

    val log = new WarnLog

    println(log.name)
    println(log.add(10, 30))

    println("*"*100)

    val log2 = new WarnLog

    val log3 = new ErrorLog() with Log{

      override val name: String = "wangwu"

      override def add(x: Int, y: Int): Int = x+y
    }

    println(log3.name)
    println(log3.age)
    println(log3.add(1,2))

    val log4 = new ErrorLog
    //println(log4.age)
  }
}

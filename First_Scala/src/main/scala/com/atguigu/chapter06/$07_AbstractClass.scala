package com.atguigu.chapter06

object $07_AbstractClass {

  abstract class Person{

    //抽象属性
    val name:String

    //具体属性
    val age:Int = 320

    //抽象方法
    def add(x:Int,y:Int):Int

    //具体方法
    def m1(x:Int) = x*x

  }

  class Student extends Person{

    override val name: String = "lisi"

    //
    override def add(x: Int, y: Int) = x+y
  }
  /**
    * 抽象类语法: 通过abstract标识的class称之为抽象类
    * 抽象方法语法: 没有方法体的方法就是抽象方法
    * 定义抽象方法的时候必须定义方法的返回值类型，如果不定义默认就是Unit
    * 具体方法: 有方法体的方法就是具体方法
    * 抽象属性:  没有初始化的属性
    * 抽象属性在定义的时候必须指定类型
    * 具体属性: 有进行初始化的属性
    */
  def main(args: Array[String]): Unit = {

    val student = new Student

    println(student.name)
    println(student.add(10,20))

    //匿名子类
    val person = new Person{

      override val name: String = "aa"

      override def add(x: Int, y: Int): Int = x*y
    }

    println(person.name)
    println(person.add(10,20))
  }
}

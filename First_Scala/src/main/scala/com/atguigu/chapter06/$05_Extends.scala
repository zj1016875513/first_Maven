package com.atguigu.chapter06

object $05_Extends {

  /*final */class Person{
    val name = "person"
    var age = 100
    private val sex = "man"

    def add(x:Int,y:Int) = x+y
  }

  class Student extends Person{
    override val name = "student"
//    override var age = 20
    override def add(x:Int,y:Int) = {
      val z = x+y
      super.add(x,z)         //最终是x+x+y
    }
  }
  /**
    *  scala中通过extends关键字实现继承
    *     哪些不能被继承?
    *         1、final修饰的class不能被继承
    *         2、private修饰的属性和方法不能被继承
    *  scala可以通过override重写父类的val的属性和方法   var属性无法重写
    *  子类可以通过super调用父类的方法
    */
  def main(args: Array[String]): Unit = {

    val person = new Student
    println(person.name)
    println(person)
    println(person.add(10, 30))

    val p:Person = new Student
    println(p.name)
  }
}

package com.atguigu.chapter06

object $08_Object {

  /**
    * scala中object就是单例对象,通过object名称就可以获取到单例对象
    *   scala中没有static关键字,object中所有属性和方法都是类似java static修饰的
    *   scala class里面的所有属性和方法都是类似java 非static修饰的,所以要想调用必须通过new创建对象才能调用
    *
    * 伴生类和伴生对象
    *   1、object与class的名称必须一样
    *   2、object与class必须在同一个源文件中
    * 伴生类和伴生对象可以互相访问对方的private修饰的成员
    *
    * apply方法: 必须定义在伴生对象中，主要用来简化伴生类对象的创建
    * 有了apply方法之后，后续可以通过 伴生对象名.apply(参数值,...)/伴生对象名(参数值,...)
    */

  val name = "zhangsan"

  def main(args: Array[String]): Unit = {

    println($08_Object)

    println($08_Object.name)

    val dog = new Dog("")

    println(dog.getAge())

    println(Dog.getName())

    val datas = Array("hello","spark","scala")

    //测试伴生对象的apply方法
    val d = Dog.apply("yyyyy")
    println(d.sex)
    val d1 = Dog("xxxxx")
    // Dog()
    println(d1.getAge())
    println(d1.sex)
  }
}

//伴生类
class Dog(val sex:String){

  private val name = "旺财"

  def getAge() = Dog.age
}

//伴生对象
object Dog{
  private val age = 20

  def getName() = {
    val dog = new Dog("")
    dog.name
  }

  //
  def apply(sex:String):Dog = new Dog(sex)
}

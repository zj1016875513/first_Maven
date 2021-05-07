package com.atguigu.chapter06

object $02_ClassFeildAndMethod {

  /**
    * java定义属性: 修饰符 类型 属性名 = 值
    * scala定义属性: 修饰符 val/var 属性名:属性类型 = 值
    *     scala val修饰的属性不可变,var修饰的属性可变
    *     scala 非private修饰的属性在外部以及class内部都可以使用, private修饰的属性只能在class内部使用
    *     scala中var修饰的变量可以通过_赋予初始值,通过_赋予初始值的时候,属性类型必须定义
    *scala定义方法: 修饰符 def 方法名(参数名:类型,...):返回值类型 = {...}
    */
  class Person{
    //定义属性
    val name = "zhangsan"

    var age = 20

    //var修饰的属性可以通过_ 赋予初始值
    var sex:Int = _

    private val address = "shenzhen"
    //定义方法
    def add(x:Int,y:Int) = x+y

    private def add(x:Int) = x* x

    //定义函数
    private val func = (x:Int,y:Int) => x+y
  }

  def main(args: Array[String]): Unit = {

    val person = new Person
    println(person.name)
    println(person.age)
    //person.name = "lisi"
    person.age = 100
    println(person.age)
    //person.address

    print("性别：")
    println(person.sex)

    print("add方法：")
    println(person.add(10, 20))

  }
}

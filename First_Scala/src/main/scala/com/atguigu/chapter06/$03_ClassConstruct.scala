package com.atguigu.chapter06

object $03_ClassConstruct {

  class Person(val name:String,var age:Int, sex:String){

    var address:String = _       //这里定义类变量是因为下面有  this.address = address  第一个address就是这里定义的address
    def this(address:String) = {
      this("zhangsan",20,"男")
      //this(20)
      this.address = address
    }

    def this(age:Int) = {
      this("beijing")     //相当于这个构造方法调用了上面的构造方法，这个年龄在上面的构造方法中重新赋值变为了20
//      this()
    }

    def getSex():String = this.sex
  }

  /**
    * java的构造方法
    *     1、定义方式: 修饰符 类名(参数类型 参数名,..){...}
    *     2、java的构造方法定义在class内部
    *
    * scala的构造方法分为两种:
    *     1、主构造器:
    *       1、语法: 修饰符 class 类名([修饰符 val/var] 属性名:类型 [= 默认值],...)
    *       主构造器中使用val/var修饰的属性与不用val/var修饰的属性的区别:
    *           使用val/var修饰的属性默认可以在class外部使用【非private修饰的】
    *           不用val/var修饰的属性只能在class内部使用
    *           主构造器中不能通过_给var修饰的属性赋予初始值
    *       2、定义位置: 类名后面的()代表主构造器
    *     2、辅助构造器
    *       1、语法: def this(参数名:参数类型,...) = {
    *         //辅助构造器第一行必须调用其他的辅助构造器或者是主构造器
    *         this(值,..)
    *       }
    *      2、定义位置: 定义在class内部
    */
  def main(args: Array[String]): Unit = {

    val person = new Person("shenzhen")
    println(person.name)
    println(person.age)
    println(person.getSex())
    println(person.address)

    println("\n***********\n")

    val p = new Person(1000)
    println(p.name)
    println(p.age)
    println(p.getSex())
    println(p.address)
  }
}

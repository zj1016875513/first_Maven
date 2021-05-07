package com.atguigu.chapter01

/**
  * object标识的是单例对象。
  * object中所有的属性和方法都是类似java static修饰的.scala中没有static关键字
  * class中所有的属性和方法都是类似java 非static修饰的。所以scala main方法必须定义在object中
  * scala没有public关键字，默认就是类似public的
  * java中main方法: public static void main(String[] args){.....}
  * Unit: 相当于java的void
  * def: defined的缩写,用来标识方法
  * args: 方法的参数名
  * Array[String]: Array代表数组,[]是用来标识泛型，[String]代表数组中存储的是String类型的属性
  *
  * scala一行如果只写一个语句，那么;可以省略。如果一行写多个语句，那么;不能省略
  *
  */
object HelloWord {

  val name = "zhangsan"

   def main(args: Array[String]): Unit = {

    //java代码
    System.out.println("hello world")

    //scala代码
    println("hello world...")

    println(HelloWord)
    println(HelloWord)
    println(HelloWord)
    println(HelloWord)
    println(HelloWord.name)
  }
}

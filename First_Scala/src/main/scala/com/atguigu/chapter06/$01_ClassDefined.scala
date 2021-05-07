package com.atguigu.chapter06

object $01_ClassDefined {

  class Person
  /**
    * java中创建类: [修饰符] class 类名{...}
    * scala中修饰符默认类似java public,scala没有public关键字
    * scala创建类: class 类名{....}
    * class的{}中如果没有任何东西，定义class的时候{}可以省略
    * 通过new创建对象的时候,如果使用默认的无参构造方法创建对象,此时()可以省略
    */
  def main(args: Array[String]): Unit = {

    val person = new Person()
    //通过new创建对象的时候,如果使用默认的无参构造方法创建对象,此时()可以省略
    //val person = new Person
    println(person)
  }
}

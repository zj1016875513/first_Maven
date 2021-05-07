package com.atguigu.chapter11

object $02_GenericClass {

  class Person[T,U](var name:T,var age:U) {

    def setName(name:T) = this.name = name

    def getName():T = this.name

    def setAge(age:U) = this.age = age

    def getAge():U = this.age


  }

  /**
    * 泛型类:  class 类名[T,U](属性名: T,..) = {
    *             def xx():U = {..}
    *         }
    * @param args
    */
  def main(args: Array[String]): Unit = {

    val person = new Person[String,Int]("zhangsan",20)

    println(person.getName())
    println(person.getAge())
  }
}

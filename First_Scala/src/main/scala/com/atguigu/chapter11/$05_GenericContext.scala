package com.atguigu.chapter11

object $05_GenericContext {

  class Person[U](var name:U)

  /**
    * 上下文语法:    T:类型
    * 上下文就是 def 方法名[T](x:T)(implicit y:类型[T]) 的缩写
    * @param args
    */
  def main(args: Array[String]): Unit = {

    println(m1[String]("lisi").name)

    println(m2("lisi").name)
  }

  def m2[T:Person](x:T) = {

    val person = implicitly[Person[T]]
    person.name = x
    person
  }

  implicit val person:Person[String] = new Person[String]("aa")
  def m1[T](x:T)(implicit person:Person[T]) = {
    person.name = x
    person
  }

}

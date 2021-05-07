package com.atguigu.test

object aa {
  class Person{
    var name="李四"
    private var address="深圳"
  }
  def main(args: Array[String]): Unit = {
    val p = new Person
    println(p.name)
  }
}

package com.atguigu.chapter06



object $13_Type {
  type MyString = String

  /**
    * 新类型语法: type 别名 = 类型
    * @param args
    */
  def main(args: Array[String]): Unit = {


    val name:MyString = "name"

    println(name)
    print(aaa())

  }


  def aaa():MyString={
    val a= "abc"
    a
  }
}

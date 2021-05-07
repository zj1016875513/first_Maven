package com.atguigu.chapter05

object $02_MethodSample {

  /**
    * 方法的简化原则:
    *   1、如果将方法体块表达式的结果值作为方法的返回值,定义方法的时候返回值类型可以省略
    *       如果方法体中有return关键字,定义方法的时候返回值类型必须定义
    *   2、如果方法体只有一行语句,{ }可以省略
    *   3、如果方法不需要参数,()可以省略
    *       1、如果定义方法的时候有(),在调用方法的时候()可有可无
    *       2、如果定义方法的时候没有(),调用方法的时候()也不能带上
    *   4、如果方法不需要返回值,那么=可以省略,=与{}不能同时省略
    */
  def main(args: Array[String]): Unit = {

    println(add2(10, 20))

    hello2
    hello()
    hello
    hello3
  }

  //标准定义方法形式
  def add(x:Int,y:Int):Int = {
    x+y
  }

  def hello():Unit = {
    println("hello....")
  }

  //1、如果将方法体块表达式的结果值作为方法的返回值,定义方法的时候返回值类型可以省略
  def add2(x:Int,y:Int) = {
    x+y
  }

  //2、如果方法体只有一行语句,{ }可以省略
  def add3(x:Int,y:Int) = x+y

  //3、如果方法不需要参数,()可以省略
  def hello2 =  println("hello....")

  //4、如果方法不需要返回值,那么=可以省略
  def hello3{
    println("hello3....")
  }
  //如果方法体中有return关键字,定义方法的时候返回值类型必须定义
  def add4(x:Int,y:Int):Int = {
    return x+y
  }

}

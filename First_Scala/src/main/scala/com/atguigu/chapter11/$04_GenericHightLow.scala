package com.atguigu.chapter11

object $04_GenericHightLow {

  class Dog

  class Pig extends Dog

  class PigSub extends Pig

  class PigSubSub extends PigSub

  //上限
  //class Test[T<:Pig](pig: T)
  //下限
  ///class Test[T>:Pig](pig: T)

  //上下限一起使用
  class Test[T>:PigSub<:Pig](pig: T)
  /**
    * 上限:   T <: 类型，代表后续传入的类型必须是指定类型或者是其子类
    * 下限:   T >: 类型,代表后续传入的类型必须是指定类型或者是其父类
    * 上下限一起使用的时候必须先写下限再写上限
    */
  def main(args: Array[String]): Unit = {


    val test = new Test(new PigSubSub)


  }
}

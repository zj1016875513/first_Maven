package com.atguigu.chapter11

object $03_GenericChange {

  class B

  class C extends B

  class Person

  class Student extends Person
  //非变
  class Tmp[T]
  //协变
  class Tmp2[+T]

  //逆变
  class Tmp3[-T]
  /**
    * 逆变: -T 两个创建的对象颠倒了泛型的父子关系
    * 协变: +T 两个创建的对象继承了泛型的父子关系
    * 非变: T  两个创建的对象没有任何关系
    * @param args
    */
  def main(args: Array[String]): Unit = {


    var list1: List[B] = List(new B,new B)
    var list2: List[C] = List(new C,new C)

    list1 = list2

    //非变
    var tmp1 = new Tmp[Person]
    var tmp2= new Tmp[Student]
    //tmp1 = tmp2
    //tmp2 = tmp1

    //协变
    var tmp3 = new Tmp2[Person]
    var tmp4 = new Tmp2[Student]
    tmp3 = tmp4

    //逆变
    var tmp5 = new Tmp3[Person]
    var tmp6 = new Tmp3[Student]
    tmp6 = tmp5
    //tmp5 = tmp6

  }
}

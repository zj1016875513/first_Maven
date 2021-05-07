package com.atguigu.chapter02

object $06_DataType {

  /**
    * java数据类型:
    *     1、基本数据类型: byte、short、int、char、long、float、double、boolean
    *     2、引用数据类型: String、集合、数组、class、Byte
    *
    * scala数据类型:
    *   Any: 所有类的父类,类似java Object
    *     AnyRef: 引用类型
    *       String、集合、数组、java class、scala class
    *           Null: 是所有引用类型的子类, 也有一个实例null,null一般用于给变量赋予初始值,在给变量赋初始值的时候,变量类型不能省略
    *     AnyVal: 值类型
    *       Byte、Short、Int、Char、Long、Float、Double、Boolean
    *       Stringops: 就是对java String的扩展
    *       Unit: 相当于java的void，有一个实例()
    *   Nothing: 所有类型的子类。是scala内部使用,开发人员不能创建Nothing对象
    *
    * @param args
    */
  def main(args: Array[String]): Unit = {

    val a: Unit = ()

    val b = null

    //b = "lisi"

    //val c:Nothing =

    //var c:Int = null
    //c = 10
  }
}

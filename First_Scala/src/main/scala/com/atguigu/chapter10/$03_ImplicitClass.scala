package com.atguigu.chapter10

import java.io.File

import scala.io.Source

object $03_ImplicitClass {

  implicit class RichFile(file:File) {

    def getLines() = {
      Source.fromFile(file).getLines()
    }

    def add(x:Int,y:Int) = x+y
  }


  //implicit def fileToRichFile(file: File):RichFile = new RichFile(file)
  /**
    * 隐式类语法:  implicit class 类名(属性名:待转换类型){....}
    * 隐式类必须定义在object/class中，不能置于最顶层
    * @param args
    */
  def main(args: Array[String]): Unit = {

    val file = new File("d:/data.txt")

    println(file.add(10, 20))
  }
}

package com.atguigu.chapter10

import com.atguigu.chapter10.$03_ImplicitClass.RichFile

import java.io.File
//import com.atguigu.chapter01.XX._
//import com.atguigu.chapter01.XX._
//import com.atguigu.chapter01.XX._
//import com.atguigu.chapter01.XX._
//import com.atguigu.chapter01.XX._
//import com.atguigu.chapter01.XX._
//import com.atguigu.chapter01.XX._
//import com.atguigu.chapter01.XX._
//import com.atguigu.chapter01.XX._
//import com.atguigu.chapter01.XX._
//import com.atguigu.chapter01.XX._
//import com.atguigu.chapter01.XX._
//import com.atguigu.chapter01.XX._
import scala.io.{BufferedSource, Source}



/*class XX{
  implicit def file2BuffedSource( file:File ):BufferedSource = {
    Source.fromFile(file,"utf-8")
  }

  implicit def double2Int( d:Double ):Int = d.toInt

  implicit def double2Int2( d:Double ):Int = (d+10).toInt
}*/

object $01_ImplicitDefined {



  /**
    * 隐式转换方法:  将一个类型自动转成另一个类型
    *     定义语法:  implicit def 方法名(参数名: 待转换类型):目标类型 = {...}
    *     隐式转换方法的调用时机:
    *         1、当前类型和目标类型不一致的时候,会自动调用隐式转换方法
    *         2、对象使用了不属于自身的属性和方法的时候,会自动调用隐式转换方法
    *     隐式转换方法解析:
    *         如果当前类型和目标类型不一致/对象使用了不属于自身的属性和方法的时候,此时会自动从当前作用域寻找有没有符合的隐式转换方法,如果找不到则报错。
    *         如果隐式转换方法定义在其他的object/class中，后续要使用的时候需要进行导入才能使用:
    *             隐式转换定义在object中,导入: import object名称._ /import object名称.隐式转换方法名称
    *             隐式转换定义在class中,导入:
    *                 val 对象名 = new class名称
    *                 import  对象名._ /import 对象名.隐式转换方法名称
    *      如果有多个隐式转换方法都符合要求，需要明确指定导入哪个隐式转换方法
    *
    * 隐式参数: 自动给方法传入一个参数值
    * 隐式类: 将一个类型自动转成另一个类型,是隐式转换方法的一种特殊的简写形式
    */
  def main(args: Array[String]): Unit = {

    //val xx = new XX
    //import xx.double2Int
//    val d:Int = 10.0

    val file = new File("d:/data.txt")

    file.getLines.foreach(println(_))


  }

}

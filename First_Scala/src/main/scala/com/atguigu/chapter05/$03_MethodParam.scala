package com.atguigu.chapter05

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

object $03_MethodParam {

  /**
    * 方法的参数:
    *     1、方法的参数可以给默认值:  def 方法名(参数名:参数类型=默认值,...):返回值类型 = {方法体}
    *         有默认值的参数一般放在参数列表的后面
    *     2、带名参数: 在调用方法的时候指定将值传递给方法的哪个参数
    *        方法名(参数名 = 值)
    *     3、可变参数: 传递的参数值的个数不确定
    *         可变参数必须放在参数列表最后面,不能与默认值参数一起使用
    *         def 方法名( 参数名:参数类型* ) = {方法体}
    *         数组/集合不能直接传递给可变参数,只能通过 数组名/集合名:_* 的形式传给可变参数
    */
  def main(args: Array[String]): Unit = {

    println(add(10, 20))

    //带名参数
    println(add2(y=30))
/*    val arr = Array[Int](1, 3, 5, 7, 9)
    println(sum(x=10, y=20, arr))*/

    val result = getPaths(7,"/user/hive/warehouse/user_info")
    //println(result)

    readFile(result:_*)
  }

  //标准形式
  def add(x:Int,y:Int):Int = x+y

  //默认值
  def add2(y:Int,x:Int =  10) = x+y

  //可变参数: 传递的参数值的个数不确定
  def sum(x:Int,y:Int,z:Int*) = x+y+z.sum


  //获取前N天的数据目录
  //pathPrefix = /user/hive/warehouse/user_info
  // n=7
  def getPaths(n:Int,pathPrefix:String) = {

    //获取当前日期
    val time = LocalDateTime.now()

    for( i <- 1 to n) yield {

      val time2= time.plusDays(-i)

      val dateStr = time2.format(DateTimeFormatter.ofPattern("yyyyMMdd"))

      s"${pathPrefix}/${dateStr}"
    }



  }

  def readFile(path: String*): Unit = {

    println(path.toList)
  }
}

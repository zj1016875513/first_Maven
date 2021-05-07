package com.atguigu.chapter09

import java.sql.{Connection, DriverManager, PreparedStatement}

import scala.util.Try

object $01_Exception {

  /**
    * java异常的处理方式:
    *     1、抛出异常:
    *       1、方法体中: throw new xxException("...")
    *       2、在方法上通过throws关键字声明抛出一样
    *     2、捕获异常
    *
    * scala异常处理方式
    *   1、抛出异常:
    *       与java不太一样，只需要在方法体中通过throw抛出异常即可，不需要在方法上通过throws关键字声明异常
    *   2、捕获异常<获取资源链接的时候>：
    *        try{
    *           ...
    *         }catch{
    *         case e:异常类型 => ..
    *         }finally{
    *           ...
    *         }
    *   3、Try(代码块).getOrElse(默认值) 如果代码块运行成功则返回代码正常执行结果，如果执行出现异常则返回默认值
    *       Try有两个子类:
    *         Success: 代表代码执行成功
    *         Failture: 代表代码运行出现异常
    *       Try有isFailture、isSuccess判断是否为Failture、success类型
    */
  def main(args: Array[String]): Unit = {

    //println(m1(10, 0))

    m2(10,0)

    val list = List(
      "1 zhangsan 20 shenzhen",
      "1 zhangsan  shenzhen",
      "1  20 shenzhen",
      "1 zhangsan 20 "
    )

    //统计年龄的总和
    //list.filter(x=>x.split(" ")(2)!="").foreach(println(_))

    list.map(x=>{
      val arr = x.split(" ")
      //val age= try{arr(2).toInt}catch {case e:Exception=>0}
      val age = Try(arr(2).toInt).getOrElse(0)
      (arr(0),arr(1),age)
    }).foreach(println(_))

    list.map(x=>{
      val arr = x.split(" ")
      //val age= try{arr(2).toInt}catch {case e:Exception=>0}
      val age: Try[Int] = Try(arr(2).toInt)
      (arr(0),arr(1),age)
    }).filter(_._3.isFailure).foreach(println(_))

  }

  def m1(a:Int,b:Int) = {
    if(b==0) throw new Exception("被除数不能为0")
    else  a/b
  }

  def m2(a:Int,b:Int) = {

    try{
      a/b
    }catch {
      case e:Exception => println(".........")
    }
  }

  def jdbcUtil() = {

    //1、加载驱动
    Class.forName("....")
    var connection:Connection = null
    var statement:PreparedStatement = null
    try{
      //2、获取connnection
      connection = DriverManager.getConnection("jdbc:mysql://hadoop102:3306/test","root","root123")
      //3、创建statemnet对象
      statement = connection.prepareStatement("select * from person where id=? and name=?")
      //4、赋值
      statement.setInt(1,101)
      statement.setString(2,"lisi")
      //5、执行sql
      statement.executeQuery()

    }catch {
      case e:Exception =>
    }finally {
      if(statement!=null)
      //6、关闭
        statement.close()
      if(connection!=null)
        connection.close()
    }

  }
}

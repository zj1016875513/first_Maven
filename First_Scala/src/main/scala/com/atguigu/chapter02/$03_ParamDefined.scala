package com.atguigu.chapter02

object $03_ParamDefined {

  /**
    * java定义变量: 类型 变量名 = 值
    *
    * scala中定义变量语法:  val/var 变量名:类型 = 值
    * val与var的区别:
    *     val定义的变量类似java final的，不能被重新赋值
    *     var定义的变量可以被重新赋值
    * scala在定义变量的时候,变量类型可以省略,scala会自动推断变量类型
    * scala在定义变量的时候必须初始化。
    * @param args
    */
  def main(args: Array[String]): Unit = {

    val name:String = "zhangsan"

    var age:Int = 20

    //name = "lisi"

    age = 100

   // age = "lisi"
  //scala在定义变量的时候,变量类型可以省略,scala会自动推断变量类型
    val address = "shenzhen"

    println(address)

    //val name2:String;

  }
}

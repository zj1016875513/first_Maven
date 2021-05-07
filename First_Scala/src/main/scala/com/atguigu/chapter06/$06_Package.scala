package com.atguigu.chapter06

import com.atguigu.chapter06.NAMEXXX

class DD{
  private[chapter06] val XX = "王五"
}

object $06_Package {

  /**
    * java中包的用法:
    *     1、导入包: import 包名.类名/import 包名.* 【导入包的位置必须在文件开头,在package声明下面】
    *     2、声明包: package com.atguigu.chapter06
    *
    * scala中包的用法:
    *     1、导入包:
    *         scala导入包的时候可以在任何地方导入。
    *         scala导入包之后只能在当前作用域和子作用域使用
    *         1、导入包下所有类: import 包名._
    *         2、导入包下某个类: import 包名.类名
    *         3、导入包下多个类: import 包名.{类名,类名,..}
    *         4、导入包下某个类,并起别名: import 包名.{类名=> 别名}
    *         5、导入包下除开某个类的所有类: import 包名.{类名=> _ , _}
    *    2、声明包:
    *       1、与java一样: package com.atguigu.chapter06
    *    3、创建包
    *       package 包名{...}
    *    4、包对象:
    *         语法: package object 包名{...}
    *         包对象中定义的非private修饰的属性和方法以及函数可以在当前包中任何地方使用
    *
    *    5、将包和访问修饰符结合使用: private[包名] 代表修饰的属性或者方法只能在指定包下使用
    */
  def main(args: Array[String]): Unit = {

    //import java.util
    //val map = new util.HashMap[String,String]()
    import java.util.{HashMap=>JavaHashMap}

    val map = new JavaHashMap[String,String]()

    val dd = new DD
    println(dd.XX)
  }


}


//class Person


  object CC{
    def main(args: Array[String]): Unit = {


      println(NAMEXXX)
    }
  }

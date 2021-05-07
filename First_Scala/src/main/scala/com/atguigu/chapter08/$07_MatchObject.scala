package com.atguigu.chapter08

object $07_MatchObject {

  case class Person(val name:String,var age:Int,address:String)

  case object Man
  /**
    * 样例类:
    *     语法: case class 类名([val/var] 属性名:属性类型,...)
    *          属性不写val/var,默认情况下就是val修饰的
    *     创建样例类对象: 类名(属性值,...)
    * 样例对象
    *     语法: case object 名称
    *
    * 普通类不能直接用于模式匹配,要想用模式匹配，必须在伴生对象中定义unapply方法，将对象解构成属性
    * @param args
    */
  def main(args: Array[String]): Unit = {

    val person1 = Person("zhangsan",20,"shenzhen")
    val person2 = Person("zhangsan",20,"shenzhen")
    var p:Person = person1
    println(p.eq(person1))
    println(person1.eq(person2))

    println("*"*100)

    person1.age = 100
    person1 match{
      case Person(a,b,c) => println(s"${a},${b},${c}")
    }

    println("*"*100)

    val dog = new Dog("wangcai",5,"shenzhen")
    dog match{
      case Dog(name,age,address) =>
        println(s"dog{${name},${age},${address}}")
    }
    //Student("zhaoliu",30,"beijing")

    val regions = List(
      Region("宝安区",School("宝安中学",Clazz("王者峡谷班",Student("蔡文姬",18)))),
      Region("宝安区",School("宝安中学",Clazz("王者峡谷班",Student("牛魔",18))))
    )

    regions.foreach(x=>println(x.school.clazz.stu.name))
  }

  case class Region(name:String,school:School)

  case class School(name:String,clazz:Clazz)

  case class Clazz(name:String,stu:Student)

  case class Student(name:String,age:Int)

}


class Dog(val name:String,var age:Int,val address:String)

object Dog{

  def apply(name:String,age:Int,address:String) = new Dog(name,age,address)

  def unapply(dog: Dog): Option[(String, Int, String)] = {
    if(dog==null) None
    else
      Some(dog.name,dog.age,dog.address)
  }
}

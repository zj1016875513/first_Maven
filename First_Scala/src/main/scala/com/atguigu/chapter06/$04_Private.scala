package com.atguigu.chapter06

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.serializer.SerializeFilter

import scala.beans.BeanProperty

object $04_Private {

  class Person{

    @BeanProperty var name = "zhangsan"

    @BeanProperty var age = 20

/*    def getName() = this.name

    def setAge(age:Int) = this.age = age

    def getAge() = this.age*/

  }

  /**
    * scala默认的set/get方法。
    *     scala默认的get方法名 = 属性名
    *     scala默认的set方法名 = 属性名=
    *
    */
  def main(args: Array[String]): Unit = {
    val person = new Person
    println(person.name)
    person.age=(100)
    println(person.age)

    person.setAge(300)
    println(person.getName)
    println(person.getAge)

    println(NAMEXXX)
    println(AGEXXX)

    //将json字符串解析成对象
    val json = """{"name":"wangwu","age":50}"""

    val p = JSON.parseObject(json,classOf[Person])

    println(p.getName)
    println(p.getAge)
    //将对象转成json字符串
    val p2 = new Person
//    print(JSON.toJSONString(p2, null.asInstanceOf[SerializeFilter]))
    println(JSON.toJSONString(p2, null.asInstanceOf[Array[SerializeFilter]]))
  }
}

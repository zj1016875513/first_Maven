package com.atguigu.chapter06

import java.io.{FileInputStream, FileOutputStream, ObjectInputStream, ObjectOutputStream}

import scala.beans.BeanProperty

object $11_Self {

  class Student

  trait ReadAndWriteObject{

//    _:Serializable =>
    this:Serializable =>
    /**
      * 从磁盘读取对象
      */
    def read() = {

      val ois = new ObjectInputStream(new FileInputStream("d:/obj.txt"))

      val obj = ois.readObject()

      ois.close()

      obj
    }

    /**
      * 将当前对象写入磁盘
      */
    def write() = {

      val oos = new ObjectOutputStream(new FileOutputStream("d:/obj.txt"))

      oos.writeObject(this)

      oos.flush()

      oos.close()
    }
  }

  /**
    * 自身类型:
    *     语法:  this:类型 =>
    *     该自身类型必须写在trait内部
    * trait自身类型其实就是提醒子类在实现trait的时候必须实现/继承指定的类型
    */
  class Person extends ReadAndWriteObject with Serializable {
    @BeanProperty
    var name:String = "zhangsan"
    @BeanProperty
    var age:Int = 20
  }

  def main(args: Array[String]): Unit = {

    val person = new Person
    person.name = "lisi"
    person.age = 100
    person.write()

    val p = new Person
    val obj = p.read().asInstanceOf[Person]
    println(obj.name)
    println(obj.age)
  }
}

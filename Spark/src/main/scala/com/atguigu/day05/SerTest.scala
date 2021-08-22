package com.atguigu.day05

import java.io.{FileInputStream, FileOutputStream, ObjectOutputStream}

import com.esotericsoftware.kryo.Kryo
import com.esotericsoftware.kryo.io.{Input, Output}

import scala.beans.BeanProperty

object SerTest {

  def main(args: Array[String]): Unit = {

    val dog = new Dog()
    dog.setName("wangwang")
    dog.setAge(20)

    //
    javaSerializable(dog)

    kryoSerializable(dog)
  }

  //kryo序列化
  def kryoSerializable(dog:Dog)={

    val kryo = new Kryo()

    val output = new Output(new FileOutputStream("d:/test_serialize/kryoser.txt"))
    kryo.writeObject(output,dog)

    output.flush()

    output.close()

    val input = new Input(new FileInputStream("d:/test_serialize/kryoser.txt"))
    val obj = kryo.readObject(input,classOf[Dog])
    println(obj.name)
    println(obj.age)
  }



  //java序列化
  def javaSerializable(dog:Dog) = {

    val oos = new ObjectOutputStream(new FileOutputStream("d:/test_serialize/javaser.txt"))

    oos.writeObject(dog)

    oos.flush()

    oos.close()
  }
}

class Dog extends Serializable {

  @BeanProperty var name:String = _

  @BeanProperty var age:Int = _
}

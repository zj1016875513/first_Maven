package com.atguigu.chapter06



import scala.util.Random

object $12_TypeCheck {

  val READ = "read"

  class Animal

  class Dog extends Animal{
    val name = "旺财"
  }

  class Pig extends Animal{
    val color = "彩色"
  }

  class PigT extends Pig


  /**
    * java中类型检查: 对象 instanceof 类型
    * java中强转: (类型)对象
    * java中获取对象的class形式: 对象.getClass()
    * java中获取类的class形式: 类名.class
    *
    * 类型检查: 对象.isInstanceOf[类型]
    * 强转: 对象.asInstanceOf[类型]
    * scala获取对象的class形式: 对象.getClass
    * scala获取类的class形式: classOf[类名]
    *
    *
    */
  def main(args: Array[String]): Unit = {


    val animal = getAnimal
    println(animal)
    if(animal.isInstanceOf[Pig]){
      val pig = animal.asInstanceOf[Pig] //animal对象向下转型为pig
      println(pig.color)
    }else{
      val dog = animal.asInstanceOf[Dog] //animal对象向下转型dog
      println(dog.name)
    }

    println(animal.getClass)
    println(classOf[Pig])
  }

  def getAnimal() = {

    val index = Random.nextInt(10)

    if(index%2==0)
      new Dog
    else{
      if(index %3 == 0 ) new PigT
      else new Pig
    }
  }
}

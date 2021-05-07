package com.atguigu.day05

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object $02_RDDSer {
  /**
    * spark算子里面的代码是在executor执行的
    * spark算子外面的代码是在Driver执行的
    * 样例类默认实现了序列化接口
    *
    * spark中有两种序列化方式: java序列化方式<默认>、kryo序列化方式
    * spark工作中一般推荐使用Kryo序列化。
    * 如何使用Kryo序列化:
    *     1、通过sparkConf设置spark序列化默认使用Kryo: .set("spark.serializer","org.apache.spark.serializer.KryoSerializer")
    *     2、注册: 注册的类在使用kryo序列化的时候不会序列化全类名,如果没有注册会序列化全类名 <可选>
    *         .registerKryoClasses(classs)
    *
    */
  def main(args: Array[String]): Unit = {

    val classs:Array[Class[_]] = Array(classOf[Student])
    val sc = new SparkContext(new SparkConf().setMaster("local[4]").setAppName("TEST").set("spark.serializer","org.apache.spark.serializer.KryoSerializer").registerKryoClasses(classs))

    val rdd = sc.parallelize(List(1,4,6,2,9,10))

    //val a = 10

    //val rdd2 = rdd.map(x=> x+a)

    val person = Person(10)

    val rdd2 = person.add(rdd)

    println(rdd2.collect().toList)

    val rdd3 = sc.parallelize(List( "1 zhangsan 20","2 lisi 30" ))


    val rdd4 = rdd3.map(x=>{
      val arr = x.split(" ")
      new Student(arr(0).toInt,arr(1),arr(2).toInt)
    })

    rdd4.foreach(x=>println(x.name))
  }


}

class Student(val id:Int,val name:String,val age:Int) extends Serializable

case class Person(val b:Int){

  def add(rdd: RDD[Int]) = {
    rdd.map(x=>x+b)
  }
}

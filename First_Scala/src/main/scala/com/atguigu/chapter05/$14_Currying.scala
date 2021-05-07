package com.atguigu.chapter05

object $14_Currying {

  /**
    * 柯里化: 有多个参数列表的方法称之为柯里化
    *
    */
  def main(args: Array[String]): Unit = {

    println(add(10, 20)(30)(40))

    val func = add2(10,20)

    val func2: Int => Int = func(30)

    val result: Int = func2(40)

    println(add2(10, 20)(30)(40))

    println(result)
  }

  /**
    * 柯里化演变过程
    * @param x
    * @param y
    * @return
    */
  def add2(x:Int,y:Int) = {

   (z:Int) => {

      (a:Int) => {

        x+y+z+a
      }
    }
  }

  def add(x:Int,y:Int)(z:Int)(a:Int) = x+y+z+a

}

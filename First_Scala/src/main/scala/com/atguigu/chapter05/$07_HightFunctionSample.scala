package com.atguigu.chapter05

object $07_HightFunctionSample {

  /**
    * 高阶函数的简化:
    *     1、直接将函数本身作为值传递
    *     2、函数的参数类型可以省略
    *     3、如果函数的参数在函数体中只使用了一次,那么可以用_代替
    *           1、如果函数体中参数的使用顺序与参数的定义顺序不一致,不能用_代替
    *           2、如果函数参数只有一个,并且在函数体中没有做任何操作,直接返回函数参数本身的时候,不能用_代替
    *           3、如果函数体中有嵌套,函数的参数在嵌套中以表达式的形式存在,此时不能用_代替
    *     4、如果函数的参数只有一个,函数参数列表的()可以省略
    */
  def main(args: Array[String]): Unit = {

    val func = (x:Int,y:Int) => x+y

    add(10,20,func)
    //1、直接将函数本身作为值传递
    val func2 = (x:Int,y:String) => y * x

    println(add(10, 20, (x: Int, y: Int) => x + y))
    //2、函数的参数类型可以省略
    println(add(10, 20, (x, y) => x + y))

    //3、如果函数的参数在函数体中只使用了一次,那么可以用_代替
    println(add(10, 20, _+_))


    println(add(10, 20, (x, y) => y-x))
    //1、如果函数体中参数的使用顺序与参数的定义顺序不一致,不能用_代替
    println(add(10, 20, _-_))
    //add(10,20,func2)

    val func3 = (x:Int) => x * x
    add2(10,func3)
    add2(10,(x:Int) => x * x)
    add2(10,(x) => x * x)


    println(add2(10,x => x))
    //2、如果函数参数只有一个,并且在函数体中没有做任何操作,直接返回函数参数本身的时候,不能用_代替
    println(add2(10, _))


    //4、如果函数的参数只有一个,函数参数列表的()可以省略
    println(add2(10, x => (x-1)*10 ))
    //如果函数体中有嵌套,函数的参数在嵌套中以表达式的形式存在,此时不能用_代替
    //println(add2(10,( _ - 1 ) + 10 ))


  }

  def add(x:Int,y:Int, func: (Int,Int)=>Int) = {
    func(x,y)
  }

  def add2(x:Int,func: Int => Int) = {
    func(x)
  }
}

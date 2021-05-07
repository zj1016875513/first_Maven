package com.atguigu.chapter05

object $18_ControlAbstract {

  /**
    * 控制抽象: 其实就是一个块表达式,但是控制抽象不能单独使用,只能作为方法参数使用,参数类型表示为   => 返回值类型
    * 控制抽象可以当做类似函数的调用,在调用的时候不能带上()
    * @param args
    */
  def main(args: Array[String]): Unit = {

    val b = {
      println("...........")
      10
    }  //会输出...........   因为初始化

    def m1(x:Int) = x * x

    m1(b)
    m1(b)
    m1(b)      //没有任何输出
    println("*"*100)    //输出100个*
    println(m1(b))  //输出100
    m1({
      println("--------------------")
      10
    })
    m1({
      println("--------------------")
      10
    })

    m1({
      println("--------------------")
      10
    })          //输出3行--------------------   因为块表达式是int类型刚好符合方法的输入类型
    println("="*100)

    val func = (x:Int) => {
      println("*********************")
      x
    }

    m1(func(10))
    m1(func(10))
    m1(func(10)) //每次都输出*********************

    println(m1(func(10)))   //本次输出一行*********************   换行后是100

    println("="*100)
    val func2 = () => {
      println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^")
    }

    def m2() = {
      func2
      func2
      func2
    }
    println("m2方法")
    m2()
    println("m2方法")

    def m3(function:  => Unit ) = {
      println(function)
      function
      function
    }
    println("m3方法")
    m3(func2)
    println("m3方法")
//    m3(func2())
//    println("m3方法")


    println("-"*100)
    m3({
      println("888888888888888888888")
    })

    println("+"*100)

    var a = 0
    while(a<=10){
      println(s"a=${a}")
      a=a+1
    }



    def loop(condition: => Boolean)(func: => Unit):Unit = {
      if(condition){
        func
        loop(condition)(func)   //loop(condition)(func)是这个loop函数的再次调用，即递归
      }
    }
    println("*"*100)

    a = 0  //上面循环有定义a，这里置a为0
    loop({
      a<=10
    })({
      println(s"a=${a}")
      a=a+1
//      100
    })


  }




}

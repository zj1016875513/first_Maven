package com.atguigu.test

  object TestChar {
  def main(args: Array[String]): Unit = {
    var name:String = "金莲"
    var age: Int = 18
    println(name+""+age)
    printf("名字：%s 年龄：%d\n",name,age)
    println(s"name=$name age=$age")
    println{
      s"""
      |name={$name}
      |age={$age}
      |""".stripMargin
    }
    println("*********")
    var n1:Int=3.5.toInt
    println(n1)
    println("*********")
    var c1:Char='a'
    println("c1="+c1)
    println("c1="+c1.toInt)
    var c2:Int='a'+1
    println("c2="+c2)
    var c3:Char=('a'+1).toChar
    println(c3)

    var cat = new Cat()
    cat =null
    sayOK()
//    test()

//    def test() : Nothing={
//      throw new Exception("提示个异常看看")
//    }
    println("********")
    println(sayOK())
  }
    def sayOK()={
      println("I am OK")
    }
}

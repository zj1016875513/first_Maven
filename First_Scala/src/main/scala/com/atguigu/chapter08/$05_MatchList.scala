package com.atguigu.chapter08

object $05_MatchList {

  def main(args: Array[String]): Unit = {

    val list: List[Int] = List(1,2,3)

    list match {
      case List(x:Int) =>
        println("list只有一个元素")
      case List(_,_*) =>
        println("list至少有一个元素")
      case List(_,_,_) =>
        println("list只有三个元素")
      case _ =>
        println("其他情况...")
    }

    println("*"*100)

    list match {
      case (x:Int) :: Nil =>
        println("list只有一个元素")
      case x :: y :: tail =>
        println(s"list至少有一个元素 ${tail}")
      case x :: y:: z:: Nil =>
        println("list只有三个元素")
      case _ =>
        println("其他情况...")
    }

    list match{
      case x:List[String] =>
        println("List[String]...")  //泛型擦除
      case x:List[Any] =>
        println("List[Any]....")
    }
  }
}

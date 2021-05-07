package com.atguigu.test

object function {
  def f6 ( p1 : String = "v1", p2 : String ) {
    println(p1 + p2);
  }

  def main(args: Array[String]): Unit = {
    println(f6("v", p2 = "s"))
    println(f6(p2 = "v2"))
  }
}

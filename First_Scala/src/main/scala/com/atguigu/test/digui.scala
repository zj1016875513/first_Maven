package com.atguigu.test

object digui {

  def main(args: Array[String]): Unit = {
    println(test(4))
    println("******")
    println(test2(4))
  }

  def test (n: Int) {
    if (n > 2) {
      test (n - 1)
    }
    println("n=" + n) // }
  }

  def test2 (n: Int) {
    if (n > 2) {
      test2 (n - 1)
    }else {
      println("n=" + n)
    }
  }

}

package com.atguigu.test

object TestFor {
  def main(args: Array[String]): Unit = {
    for(i <-1 to 10){
      println("测试"+i)
    }
    for(i <-1 to 10 if (i%3)!=0){
      println(i)
    }

    for (i <- 1 to 10 by 2) {
      println("i=" + i)
    }

    for(i <- 1 to 3; j <- 1 to 3) {
      println(" i =" + i + "; j = " + j)
    }

    for(i <- 1 to 3; j = 4 - i) {
      println("i=" + i + " j=" + j)
    }



  }
}

package com.atguigu.test

object T1 {

  def main(args: Array[String]): Unit = {

    val datas =Array("spark","hello","scala","python")

    def change(A:Array[String]):Array[Int]={

      val B= new Array[Int](A.length)

//      println(B.length)
      for( i<- 0 to A.length-1){
        B(i)=A(i).length
      }
      B
    }
    println(change(datas).toList)
  }
}

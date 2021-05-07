package com.atguigu.chapter04

object $04_For {

  /**
    * for基本语法: for(变量名 <- 数组/集合/表达式) {....}
    * 守卫: for(变量名 <- 数组/集合/表达式 if(布尔表达式)) {....}
    * 步长: for(变量名 <- start to end by step if(布尔表达式)) {....}
    * 嵌套循环: for(变量名 <- 数组/集合/表达式; 变量名2<-  数组/集合/表达式;..) {....}
    * 嵌套循环+ 守卫: for(变量名 <- 数组/集合/表达式 if(布尔表达式); 变量名2<-  数组/集合/表达式 if(布尔表达式);..) {....}
    * 嵌入变量: for(变量名 <- 数组/集合/表达式 if(布尔表达式); 变量名=值 ;变量名2<-  数组/集合/表达式 if(布尔表达式);..) {....}
    * yield表达式: for(变量名 <- 数组/集合/表达式) yield{....}
    */
  def main(args: Array[String]): Unit = {

    //基本for循环
    for( i <- 1 to 10){
      println(i)
    }

    /**
      * for(int i=0;i<=10;i++){
      * if(i%2==0){
      * sout(i)
      * }
      * }
      */
    //for循环+if判断
    println("*" * 100)
    for(i<- 0 to 10){
      //println(s"i*i=${i*i}")
      if(i%2==0){
        //println()
        if(i%4==0)
          println(i)
      }
    }

    println("*" * 100)
    for(i<- 0 to 10 if(i%2==0) if(i%4==0)) {
      println(i)
    }
    println("*" * 100)
    for(i<- 1 to 10 by 3){
      println(s"i=${i}")
    }

    //嵌套for循环
    /**
      * for(int i=0;i<=10;i++){
      *   for(int j=0;j<=i;j++){
      *
      *   }
      * }
      */
    println("*" * 100)
    for(i<- 1 to 10){
      if(i%2==0){
        //println(".........")
        val k = i*i
        for(j<- 0 to k){
          if(j%4==0){

            println(s"i+j=${i+j}")
          }
        }
      }

    }

    //scala嵌套for
    println("*" * 100)
    for(i<- 1 to 10 if(i%2==0); k=i*i;j<- 0 to k if(j%4==0)){
      println(s"i+j=${i+j} k=${k}")
    }
    println("*" * 100)

    val result = for(i<- 1 to 10) yield {
      println(s"i=${i}")
      i*i
    }

    println(result.toList)

    println((1 to 10).toList)

    println("#"*100)
    (1 to 10).foreach(println(_))   //args.foreach((arg :String) => println(arg))
    println("#"*100)
    val args = (1 to 10).toList
    args.foreach((a:Int)=>println(a))
  }
}

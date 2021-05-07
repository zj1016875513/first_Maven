package com.atguigu.chapter04

object $02_If {

  /**
    * scala中流程控制有:
    *   分支: if
    *   循环：for while do-while
    *   scala中没有switch流程控制
    *
    * if的使用:
    *     1、单分支: if(布尔表达式){..}
    *     2、双分支: if(布尔表达式){..}else{...}
    *     3、多分支: if(布尔表达式){..}else if(布尔表达式){...} ... else{...}
    *  scala中if分支有返回值的,返回值是满足条件的分支的{}中最后一个表达式的结果值
    * @param args
    */
  def main(args: Array[String]): Unit = {

    val a = 10
    //1、单分支: if(布尔表达式){..}
    val c = if(a%2==0){
      println("a%2==0")
      val a1 = 20
      val a2 = 30
      a1+a2
    }

    println(c)

    //双分支: if(布尔表达式){..}else{...}
    val d = if(a%3==0){
      println("a%2==0")
      val a1 = 20
      val a2 = 30
      a1+a2
    }else{
      "string"
    }

    println(d)

    // 3、多分支: if(布尔表达式){..}else if(布尔表达式){...} ... else{...}

    val e = if(a%4==0){
      println("a%4==0.............")
      10+20
    }else{
      if(a%3==0){
        println("a%3==0.............")
        10
      }else if(a%2==0){
        println("a%2==0.............")
        20
      }else{
        println("其他....")
        30
      }

    }

    println(e)

  }
}

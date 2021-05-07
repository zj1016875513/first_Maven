package com.atguigu.chapter07

object $03_ImmutableList {

  /**
    * 创建方式:
    *   1、List[元素类型](初始元素,...)
    *   2、初始元素 :: 初始元素 :: .. Nil/list集合
    *   Nil就是空集合,Nil一般也用来给不可变List赋予初始值,赋值的时候变量类型必须定义
    *
    *   :: 是添加单个元素
    *   ::: 是添加一个集合所有元素
    * @param args
    */
  def main(args: Array[String]): Unit = {

    //创建
    val list = List[Int](10,2,5,6,8)
    println(list)

    var list2:List[Int] = Nil
    list2 = list
    val list3 = 10 :: 2 :: 5 :: Nil
    val list4 = 10 :: 2 :: 5 :: list
    println(list3)
    println(list4)
    println("\n")

    //添加数据
    val list5 = list.+:(20)
    println(list5)
    println(list)

    val list6 = list.:+(30)
    println(list6)

    val list7 = 40 :: list //添加单个元素
    println("list7="+list7)

    val list8 = list.::(50)
    println(list8)

    print("************")

    val list9 = list.++(Array(20,30,40))
    println(list9)

    val list10 = list.++:(Array(60,70,80))
    println(list10)

    val list11 = list ::: List(100,200,300) //添加整个集合
    println(list11)

    //获取元素
    println(list(0))

    //修改元素
/*    list(0)=100
    println(list)*/
    val list12 = list.updated(0,100)
    println(list12)

  }
}

package com.atguigu.chapter07


object $11_CollectionDerive {

  def main(args: Array[String]): Unit = {

    val list = List(10,3,6,2,1,9,40,10,5,7)

    //去重
    val list2 = list.distinct
    println(list2)

    //删除前多少个元素
    val list3 = list.drop(3)
    println(list3)

    //删除后多少个元素
    val list4 = list.dropRight(3)
    println(list4)

    //获取第一个元素
    println(list.head)

    //获取最后一个元素
    println(list.last)
    
    //获取除开最后一个元素的所有元素
    val list5 = list.init
    println(list5)

    //反转
    val list6 = list.reverse
    println(list6)

    //获取子集合
    val list7 = list.slice(2,5)   //索引从  2 until 5
    println(list7)
    println("@"*100)
    //滑窗
    val list8 = list.sliding(2,3).toList  //从开头开始，取前两位变为list 跳3位之后继续
    val list8_1 =list.sliding(2,2).toList
    println("list="+list)
    println(list8)
    println(list8_1)
    println("#"*100)
    //除开第一个元素的所有元素
    val list9 = list.tail
    println(list9)

    //获取前多少个元素
    val list10 = list.take(3)
    println(list10)

    //获取后多少个元素
    val list11 = list.takeRight(3)
    println(list11)
    println("%"*100)
    //交集[两个集合都有的元素]
    val list12 = List(1,2,3,4,5)
    val list13 = List(4,5,6,7,8)
    val list14 = list12.intersect(list13) //两个集合中相同的元素
    println(list14)
    //并集[两个集合所有元素合并,不会去重]
    val list15 = list12.union(list13) //两个集合中所有的元素
    println(list15)
    //差集[A差B就是取出A中有B中没有的数据]
    val list16 = list12.diff(list13)
    println(list16)

    //拉链
    val list17 = List("zhangsan","lisi","wangwu","zhaoliu")
    val list18 = List(20,15,30)
    val list19 = list17.zip(list18)
    println(list19)

    //反拉链
    val list20 = list19.unzip
    println(list20)

    println("*"*100)
    //将元素与角标拉链
    val list21 = list.zipWithIndex
    println(list21)
  }
}

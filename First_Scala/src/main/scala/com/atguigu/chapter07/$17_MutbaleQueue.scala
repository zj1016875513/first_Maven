package com.atguigu.chapter07

import scala.collection.mutable

object $17_MutbaleQueue {

  def main(args: Array[String]): Unit = {
    //创建方式
    val queue = mutable.Queue[Int](10,3,5,1,7,8)

    //添加数据
    val queue2 = queue.+:(20)
    println(queue2)

    val queue3 = queue.:+(30)
    println(queue3)
    queue.+=(40)
    println(queue)
    queue.+=:(50)

    println(queue)

    val queue5 = queue.++(List(100,200))
    println(queue5)

    val queue6 = queue.++:(List(100,200))
    println(queue6)

    queue.++=(List(300,400))
    println(queue)

    queue.enqueue(500,250)
    println(queue)

    //删除数据
    println(queue.dequeue())
    println(queue)

    //获取数据
    println(queue(0))

    //修改数据
    queue(0)=100
    println(queue)

  }
}

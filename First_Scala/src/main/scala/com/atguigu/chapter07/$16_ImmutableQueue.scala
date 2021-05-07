package com.atguigu.chapter07

import scala.collection.immutable.Queue

object $16_ImmutableQueue {
  /**
    * 队列: 先进先出
    * @param args
    */
  def main(args: Array[String]): Unit = {

    //创建方式
    val queue = Queue[Int](10,2,3,4,5,7)

    //添加数据
    val queue2 = queue.+:(20)
    println(queue2)

    val queue3 = queue.:+(30)
    println(queue3)

    val queue4 = queue.++(List(40,50,20))
    println(queue4)

    val queue5 = queue.++:(List(60,50,90))
    println(queue5)

    val queue6 = queue.enqueue(100)
    println(queue6)

    //删除数据
    val dequeue = queue.dequeue
    println(dequeue)

    //获取数据
    println(queue(0))

    //修改数据
    //queue(0) = 100
    //println(queue)
    val queue8 = queue.updated(0,100)
    println(queue8)
  }
}

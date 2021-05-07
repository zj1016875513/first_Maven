package com.atguigu.chapter06

object $10_TraitMethod {

  trait ParentLog{
    def log(msg:String) = {
      println(s"ParentLog:${msg}")
    }
  }

  trait Log extends ParentLog{

    override def log(msg:String) = {
      println(s"Log:${msg}")
      //super.log(msg)
    }
  }

  trait Log2 extends Log{

    override def log(msg:String) = {
      println(s"Log2:${msg}")
      //super.log(msg)
    }
  }

  trait Log3 extends Log{

    override def log(msg:String) = {
      println(s"Log3:${msg}")
      //super.log(msg)

    }
  }

  class WarnLog extends Log with Log2  with Log3{

    override def log(msg: String): Unit = {
      println(s"WarnLog:${msg}")
      //super[Log2].log("hello....")
      super.log(msg)
    }

  }

  /**
    * 特质可以多实现，如果多个特质中都有一样的方法【方法名一样，参数列表一样】，此时必须要求子类重写同名方法
    * 子类重写同名方法之后，可以通过super调用父特质的同名方法，调用的时候默认调用的是最后一个特质的同名方法。
    * 如果想要指定调用某个父特质的同名方法可以通过 super[特质名].同名方法(..)
    *
    * 子类重写同名方法之后，如果继承的多个父trait有关系【都有一个共同的父trait】，此时再通过super调用同名方法的时候，是按照继承顺序从右向左开始执行
    * @param args
    */
  def main(args: Array[String]): Unit = {

    val log = new WarnLog

    log.log("出现了异常!!!!")  //最右边为log3 所以先输出WarnLog  再输出 Log3   调用的时候默认调用的是最后一个特质的同名方法
  }
}

package test

import scala.collection.mutable.ListBuffer
import scala.util.Random


object RandomOptions {

  def apply[T](opts: RanOpt[T]*): RandomOptions[T] = {

    val randomOptions = new RandomOptions[T]()

    for (opt <- opts) {
      // 累积总的权重： 8 + 2
      randomOptions.totalWeight += opt.weight

      // 根据每个元素的自己的权重，向buffer中存储数据。权重越多存储的越多
      for (i <- 1 to opt.weight) {
        // 男 男 男 男 男 男 男 男 女 女
        randomOptions.optsBuffer += opt.value
      }
    }

    randomOptions
  }

  def main(args: Array[String]): Unit = {

    for (i <- 1 to 10) {
      println(RandomOptions(RanOpt("男", 8), RanOpt("女", 2)).getRandomOpt)
      //Scala中obj(arg)的语句实际是在调用该对象的apply方法，即obj.apply(arg)。
    }
  }
}


// value值出现的比例，例如：(男，8) (女:2)
case class RanOpt[T](value: T, weight: Int)

class RandomOptions[T](opts: RanOpt[T]*) {

  var totalWeight = 0
  var optsBuffer = new ListBuffer[T]

  def getRandomOpt: T = {
    // 随机选择：0-9
    val randomNum: Int = new Random().nextInt(totalWeight)
    // 根据随机数，作为角标取数
    optsBuffer(randomNum)
  }
}


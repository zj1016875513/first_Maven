package test

import org.apache.spark.SparkConf
import org.apache.spark.sql.catalyst.expressions.Second
import org.apache.spark.sql.connector.read.streaming.SparkDataStream
import org.apache.spark.streaming.{Seconds, StreamingContext}

object d1 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[4]").setAppName("wc")
    val ssc = new StreamingContext(conf, Seconds(5))
    ssc.sparkContext.setLogLevel("error")
    val scts = ssc.socketTextStream("hadoop102", 9999)
    val ds = scts.flatMap(_.split(" ")).map(x=> {
      Thread.sleep(2000)
      (x, 1)
    }).reduceByKey(_+_)

    ds.print()
    ssc.start()
    ssc.awaitTermination()
  }

}

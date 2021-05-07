package com.atguigu.day10

import java.sql.{Connection, DriverManager, PreparedStatement}
import java.text.SimpleDateFormat
import java.util.Date

import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import org.apache.spark.streaming.{Seconds, StreamingContext}

object Test {

  def main(args: Array[String]): Unit = {

    //创建StreamingContext
    val ssc = new StreamingContext(new SparkConf().setMaster("local[4]").setAppName("test"),Seconds(5))
    ssc.sparkContext.setLogLevel("error")
    //从kafka读取数据
    val topics = Array("stream")
    //设置消费者参数
    val params = Map[String,Object](
      //设置key的反序列化器
      "key.deserializer" -> "org.apache.kafka.common.serialization.StringDeserializer",
      //设置value的反序列化器
      "value.deserializer" -> "org.apache.kafka.common.serialization.StringDeserializer",
      //设置topic所在集群地址
      "bootstrap.servers" -> "hadoop102:9092,hadoop103:9092,hadoop104:9092",
      //消费者组的id
      "group.id" -> "ks01",
      //设置消费者组第一次开始消费topic的时候从哪个位置开始消费
      "auto.offset.reset" -> "earliest",
      //是否自动提交offset
      "enable.auto.commit" -> "true"
    )

    val datas = KafkaUtils.createDirectStream[String,String](ssc,LocationStrategies.PreferConsistent,ConsumerStrategies.Subscribe[String,String](topics,params))
    //过滤黑名单用户数据
    val filterData = datas.map(record=>{
      val message = record.value()
      val arr = message.split(" ")
      //(时间,区域,城市,用户id,广告id)
      (arr.head,arr(1),arr(2),arr(3),arr(4))
    }).mapPartitions(it=>{

      var connection:Connection = null
      var statement:PreparedStatement = null
      var resultList = List[(String,String,String,String,String)]()
      try{
        connection = JdbcUtils.getConnection
        statement = connection.prepareStatement("select userid from black_list where userid=?")

        it.foreach{
          case (time,area,city,userid,adid) =>
            statement.setInt(1,userid.toInt)
            val resultSet = statement.executeQuery()
            //对于黑名单用户数据丢弃
            if(!resultSet.next())   {
              resultList = (time,area,city,userid,adid) :: resultList
            }
        }
      }catch {
        case e:Exception => e.printStackTrace()
      }finally {
        if(statement!=null)
          statement.close()
      }
      resultList.toIterator
    })
    //缓存数据，便于后续job执行的时候使用
    filterData.cache()
    //------------------------------------------------第一个需求--------------------------------------------
    //统计每天每个用户点击每个广告的次数<当前批次>
    $02_Test.run(filterData)
    //----------------------------------------------第二个需求-------------------------------
    //1、统计当前批次的点击数
    $03_Test.run(filterData)
    //------------------------------------------------第三个需求----------------------------------------
    //1、按照广告id、时间[HH:mm]分组统计-在窗口中操作[窗口长度=2分钟  滑动长度=1分钟]
    $04_Test.run(filterData)
    //启动程序
    ssc.start()
    //阻塞
    ssc.awaitTermination()


  }
}

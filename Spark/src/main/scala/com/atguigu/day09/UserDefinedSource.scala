package com.atguigu.day09

import java.io.{BufferedReader, InputStreamReader}
import java.net.Socket

import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.receiver.Receiver
//从socket采集数据
class UserDefinedSource(hostName:String,port:Int) extends Receiver[String](StorageLevel.MEMORY_AND_DISK){
  //receiver启动调用
  override def onStart(): Unit = {

    new Thread(){
      override def run(): Unit = {
        //采集数据
        receive
      }
    }.start()
  }

  //采集数据
  def receive(): Unit ={

    //创建socket
    val socket = new Socket(hostName,port)
    //获取socket流得到数据
    val br = new BufferedReader(new InputStreamReader(socket.getInputStream))
    //保存，等到一个批次之后统一处理
    var line = br.readLine()
    while(line!=null){
      //保存数据
      store(line)
      //读取下一行数据
      line = br.readLine()
    }
    //关闭流
    br.close()
    socket.close()
  }


  //receiver停止的时候调用
  override def onStop(): Unit = {

  }
}

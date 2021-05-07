package com.atguigu.day10

import java.sql.{Connection, PreparedStatement}
import java.util.Properties

import com.alibaba.druid.pool.DruidDataSourceFactory
import javax.sql.DataSource

object JdbcUtils {


  //初始化连接池
  var dataSource: DataSource = init()

  //初始化连接池方法
  def init(): DataSource = {

    val properties = new Properties()

    properties.setProperty("driverClassName", "com.mysql.jdbc.Driver")
    properties.setProperty("url", "jdbc:mysql://hadoop102:3306/test")
    properties.setProperty("username", "root")
    properties.setProperty("password","123456")
    properties.setProperty("maxActive", "5")

    DruidDataSourceFactory.createDataSource(properties)
  }

  //获取MySQL连接
  def getConnection: Connection = {
    dataSource.getConnection
  }


  def executeUpdate(param:Array[Any],statement:PreparedStatement) = {

    for(i<- 0 until param.length){
      statement.setObject(i+1,param(i))
    }
    statement.executeUpdate()

  }



}

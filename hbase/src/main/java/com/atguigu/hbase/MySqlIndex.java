package com.atguigu.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class MySqlIndex {
    /**
     * 二级索引: <重点></>
     *      原因: 如果直接根据字段的值从hbase查询，通过元数据不知道数据在哪个region，所以需要全部扫描，性能慢
     *      原理: 将常用的查询字段、rowkey保存在es/mysql等地方,后续查询的时候先从mysql/es得到rowkey,然后再根据rowkey从hbase查询详细信息
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        MySqlIndex mySqlIndex = new MySqlIndex();

        //保存数据
        //mySqlIndex.save();

        //查询数据
        String name = "zhangsan-3";

        String rowkey = mySqlIndex.queryMysql(name);

        mySqlIndex.queryHbase(rowkey);
    }

    public void save() throws Exception{
        //造数据
        for(int i=0;i<=99;i++){
            //
            String id = "100"+i;
            String name = "zhangsan-"+i;
            int age = 20+i;

            saveMysql(id,name,age);
            saveHbase(id,name,age,"shenzhen");
        }
    }

    public void saveMysql(String id,String name,int age) throws Exception{
        //获取连接
        Connection connection = DriverManager.getConnection("jdbc:mysql://hadoop102:3306/test","root","root123");
        //获取statement对象
        PreparedStatement statement = connection.prepareStatement("insert into student_2 values(?,?,?)");
        //执行保存
        statement.setString(1,id);
        statement.setString(2,name);
        statement.setInt(3,age);
        statement.executeUpdate();
        //关闭
        connection.close();
    }

    public void saveHbase(String id,String name,int age,String address) throws Exception{
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum","hadoop102:2181,hadoop103:2181,hadoop104:2181");
        org.apache.hadoop.hbase.client.Connection connection = ConnectionFactory.createConnection(conf);

        Table table = connection.getTable(TableName.valueOf("student_new"));
        Put put = new Put(id.getBytes());
        put.addColumn("base_info".getBytes(),"name".getBytes(),name.getBytes());
        put.addColumn("base_info".getBytes(),"age".getBytes(), Bytes.toBytes(age));
        put.addColumn("base_info".getBytes(),"address".getBytes(), Bytes.toBytes(address));

        table.put(put);

        table.close();
        connection.close();
    }

    public void query(){

    }

    public void queryHbase(String rowkey) throws Exception{
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum","hadoop102:2181,hadoop103:2181,hadoop104:2181");
        org.apache.hadoop.hbase.client.Connection connection = ConnectionFactory.createConnection(conf);

        Table table = connection.getTable(TableName.valueOf("student_new"));

        Get get = new Get(rowkey.getBytes());
        Result result = table.get(get);

        //4、结果展示
        List<Cell> cells = result.listCells();
        for(Cell cell:cells){
            //获取rowkey
            String row = new String(cell.getRowArray(), cell.getRowOffset(), cell.getRowLength());
            //获取列簇名
            String family = new String(cell.getFamilyArray(), cell.getFamilyOffset(), cell.getFamilyLength());
            //获取列限定符名称
            String qualifier = new String(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength());
            //获取value值
            if(family.equals("base_info") && qualifier.equals("age")){
                int value = Bytes.toInt(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                System.out.println("rowkey="+rowkey+",family="+family+",qualifier="+qualifier+",value="+value);

            }
            else{

                String value = new String(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                System.out.println("rowkey="+rowkey+",family="+family+",qualifier="+qualifier+",value="+value);

            }


        }
        //5、关闭
        table.close();
        connection.close();
    }

    /**
     * 根据指定字段从mysql获取hbase rowkey
     * @param name
     * @return
     * @throws Exception
     */
    public String queryMysql(String name) throws Exception{
        //获取连接
        Connection connection = DriverManager.getConnection("jdbc:mysql://hadoop102:3306/test","root","root123");
        //获取statement对象
        PreparedStatement statement = connection.prepareStatement("select id from student_2 where name=?");

        statement.setString(1,name);

        ResultSet resultSet = statement.executeQuery();
        if(resultSet.next()) {
            return resultSet.getString("id");
        }else{
            return null;
        }
    }
}

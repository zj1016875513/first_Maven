package com.atguigu.hbase1;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class hbaseTest {
    private Connection connection;
    private Admin admin;

    @Before
    public void begin() throws IOException {
        Configuration conf = HBaseConfiguration.create();
        //参数在hbase-site.xml文件中配置的zk参数
        conf.set("hbase.zookeeper.quorum","hadoop102:2181,hadoop103:2181,hadoop104:2181");
        connection = ConnectionFactory.createConnection(conf);
        admin = connection.getAdmin();
    }

    @After
    public void end() throws IOException {
        admin.close();
        connection.close();
    }

@Test
    public void testTableExists() throws IOException {
        boolean test = admin.tableExists(TableName.valueOf("test"));
        System.out.println(test);
    }

    @Test
    public void createNameSpace() throws IOException {
        NamespaceDescriptor ttt = NamespaceDescriptor.create("ttt").build();
        admin.createNamespace(ttt);
    }

    @Test
    public void list_nameSpace() throws IOException {
        NamespaceDescriptor[] namespaceDescriptors = admin.listNamespaceDescriptors();
        for (NamespaceDescriptor ns : namespaceDescriptors) {
            System.out.println(ns.getName());
        }
    }

    @Test
    public void nameSpaceTable() throws IOException {
        TableName[] defaults = admin.listTableNamesByNamespace("default");
        for (TableName aDefault : defaults) {

            System.out.println(aDefault.getNameAsString());
        }
    }

    @Test
    public void dropamespace() throws IOException {
        TableName[] ttts = admin.listTableNamesByNamespace("ttt");
        for (TableName ttt : ttts) {
            admin.disableTable(ttt);
            admin.deleteTable(ttt);
        }
        admin.deleteNamespace("ttt");
    }

    @Test
    public void createTable() throws IOException {
        ColumnFamilyDescriptor family1 = ColumnFamilyDescriptorBuilder.newBuilder("info1".getBytes()).build();
        TableName aaa = TableName.valueOf("AAA");
        TableDescriptor build = TableDescriptorBuilder.newBuilder(aaa).setColumnFamily(family1).build();
        admin.createTable(build);
    }
    @Test
    public void alterTable() throws Exception{
        ColumnFamilyDescriptor f1 = ColumnFamilyDescriptorBuilder.newBuilder("info1".getBytes()).setMaxVersions(3).build();
        ColumnFamilyDescriptor f2 = ColumnFamilyDescriptorBuilder.newBuilder("info2".getBytes()).setMaxVersions(2).build();
        TableDescriptor table = TableDescriptorBuilder.newBuilder(TableName.valueOf("AAA")).setColumnFamily(f1).setColumnFamily(f2).build();
        admin.modifyTable(table);
    }
    @Test
    public void put() throws Exception{
        Table aaa = connection.getTable(TableName.valueOf("AAA"));
        Put put = new Put("1001".getBytes());
        put.addColumn("info1".getBytes(),"name".getBytes(),"zhangsan".getBytes());
        put.addColumn("info1".getBytes(),"age".getBytes(), Bytes.toBytes(20));
        put.addColumn("info2".getBytes(),"address".getBytes(),"shenzhen".getBytes());
        aaa.put(put);
        aaa.close();
    }
    @Test
    public void putList() throws Exception{
        Table aaa = connection.getTable(TableName.valueOf("AAA"));
        Put put=null;
        ArrayList<Put> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            put = new Put(("300" + i).getBytes());
            put.addColumn("info1".getBytes(),"name".getBytes(),("lisi-"+i).getBytes());
            put.addColumn("info1".getBytes(),"age".getBytes(),Bytes.toBytes(30+i));
            list.add(put);
        }
        aaa.put(list);
        aaa.close();
    }

    @Test
    public void  get() throws Exception{
        Table aaa = connection.getTable(TableName.valueOf("AAA"));
        Get get = new Get("3000".getBytes());
        get.addColumn("info1".getBytes(),"name".getBytes());
        Result result = aaa.get(get);
        List<Cell> cells = result.listCells();
        for (Cell cell : cells) {
            byte[] tt = cell.getValueArray();
            System.out.println(new String(tt));
            String s = new String(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());//是为了只取value的值
            System.out.println(s);
        }
        aaa.close();
    }
//    @Test




}

package com.atguigu.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HbaseTest00 {

    private Connection connection;
    private Admin admin;
    /**
     * 初始化
     * @throws Exception
     */
    @Before
    public void init() throws Exception{

        //获取hbase连接
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum","hadoop102:2181,hadoop103:2181,hadoop104:2181");

        connection = ConnectionFactory.createConnection(conf);
        //获取admin对象
        admin = connection.getAdmin();
    }
    /**
     * 创建Namespace: create_namepase
     */
    @Test
    public void createNamespace() throws IOException {
        //创建命名空间
        NamespaceDescriptor descriptor = NamespaceDescriptor.create("bigdata").build();
        admin.createNamespace(descriptor);
    }

    /**
     * 查看所有命名空间： list_namespace
     * @throws Exception
     */
    @Test
    public void listNamespace() throws Exception{

        NamespaceDescriptor[] namespaceDescriptors = admin.listNamespaceDescriptors();

        for(NamespaceDescriptor desc: namespaceDescriptors){
            System.out.println(desc.getName());
        }
    }

    /**
     * 查看命名空间下所有表
     * @throws Exception
     */
    @Test
    public void listNamespaceTables() throws Exception{

        TableName[] tableNames = admin.listTableNamesByNamespace("default");

        for(TableName  table: tableNames){
            System.out.println(table.getNameAsString());
        }
    }

    /**
     * 删除命名空间
     * @throws Exception
     */
    @Test
    public void dropNamespace() throws Exception{

        //获取命名空间下所有表
        TableName[] tableNames = admin.listTableNamesByNamespace("bigdata");

        //删除命名空间下所有表
        for(TableName table: tableNames){
            //禁用表
            admin.disableTable(table);
            //删除表
            admin.deleteTable(table);
        }
        //删除命名空间
        admin.deleteNamespace("bigdata");

    }

    /**
     * 创建表
     * @throws Exception
     */
    @Test
    public void createTable() throws Exception{

        //描述列簇
        ColumnFamilyDescriptor baseInfo = ColumnFamilyDescriptorBuilder.newBuilder("base_info".getBytes())
                //设置列簇的版本数
                .setMaxVersions(3)
                .build();
        ColumnFamilyDescriptor extraInfo = ColumnFamilyDescriptorBuilder.newBuilder("extra_info".getBytes())
                .setMaxVersions(2)
                .build();
        //描述表
        //设置表名
        TableName tableName = TableName.valueOf("student_new");
        TableDescriptor tableDescriptor = TableDescriptorBuilder.newBuilder(tableName)
                //设置列簇
                .setColumnFamily(baseInfo)
                .setColumnFamily(extraInfo)
                .build();
        //创建表
        admin.createTable(tableDescriptor);
    }

    /**
     * 修改表
     * @throws Exception
     */
    @Test
    public void alterTable() throws Exception{

        //描述列簇
        ColumnFamilyDescriptor baseInfo = ColumnFamilyDescriptorBuilder.newBuilder("base_info".getBytes())
                //设置列簇的版本数
                .setMaxVersions(3)
                .build();
        ColumnFamilyDescriptor extraInfo = ColumnFamilyDescriptorBuilder.newBuilder("extra_info".getBytes())
                .setMaxVersions(2)
                .build();
        //设置表描述信息
        TableDescriptor descriptor = TableDescriptorBuilder.newBuilder(TableName.valueOf("student_new"))
                .setColumnFamily(baseInfo)
                .setColumnFamily(extraInfo)
                .build();

        admin.modifyTable(descriptor);

    }

    /**
     * 单条数据插入
     */
    //put '表名','rowkey','列簇:列限定符',value
    @Test
    public void put() throws Exception{

        //获取table对象
        Table student = connection.getTable(TableName.valueOf("student"));
        //封装数据
        Put data = new Put("1001".getBytes());
        data.addColumn("base_info".getBytes(),"name".getBytes(),"zhangsan".getBytes());
        data.addColumn("base_info".getBytes(),"age".getBytes(), Bytes.toBytes(20));
        data.addColumn("extra_info".getBytes(),"address".getBytes(), "shenzhen".getBytes());
        //插入数据
        student.put(data);
        //关闭
        student.close();
    }

    @Test
    public void putList() throws Exception{
        //获取table对象
        Table student = connection.getTable(TableName.valueOf("student"));

        //封装数据
        List<Put> lists = new ArrayList<Put>();
        Put put = null;
        for(int i=0;i<=10;i++){
            put = new Put( ("100"+i).getBytes() );
            put.addColumn("base_info".getBytes(),"name".getBytes(),("zhangsan-"+i).getBytes());
            put.addColumn("base_info".getBytes(),"age".getBytes(),Bytes.toBytes(20+i));

            lists.add(put);
        }

        //插入
        student.put(lists);

        //关闭
        student.close();
    }

    /**
     * 根据rowkey查询数据<单条查询></>
     * @throws Exception
     */
    @Test
    public void get() throws Exception{

        //1、获取table对象
        Table table = connection.getTable(TableName.valueOf("student"));
        //2、封装数据
        //查询整行数据
        Get get = new Get("10010".getBytes());
        //查询某个列的数据
        //get.addColumn("base_info".getBytes(),"name".getBytes());
        //查询某个列簇的数据
        //get.addFamily("base_info".getBytes());
        //设置版本数
        //get.readVersions(2);
        //查询指定时间戳的数据
        get.setTimestamp(1616728767270L);
        //3、查询
        Result result = table.get(get);
        //4、结果展示
        List<Cell> cells = result.listCells();
        for(Cell cell:cells){
            //获取rowkey
            String rowkey = new String(cell.getRowArray(), cell.getRowOffset(), cell.getRowLength());
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
    }

    /**
     * 一次查询多个rowkey对应的数据<批量查询></>
     * @throws Exception
     */
    @Test
    public void getList() throws Exception{
        //获取table
        Table table = connection.getTable(TableName.valueOf("student"));
        //封装数据
        List<Get> lists = new ArrayList<Get>();
        Get get = null;

        for(int i=5;i<=7;i++){
            get = new Get( ("100"+i).getBytes() );
            get.addFamily("base_info".getBytes());

            lists.add(get);
        }
        //查询
        Result[] results = table.get(lists);
        //结果展示
        for(Result row: results){

            List<Cell> cells = row.listCells();

            for(Cell cell:cells){
                //获取rowkey
                String rowkey = Bytes.toString(CellUtil.cloneRow(cell));
                String family = Bytes.toString(CellUtil.cloneFamily(cell));
                String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
                if( family.equals("base_info") && qualifier.equals("age")){
                    int value = Bytes.toInt(CellUtil.cloneValue(cell));
                    System.out.println("rowkey="+rowkey+",family="+family+",qualifier="+qualifier+",value="+value);

                }else{

                    String value = Bytes.toString(CellUtil.cloneValue(cell));
                    System.out.println("rowkey="+rowkey+",family="+family+",qualifier="+qualifier+",value="+value);

                }

            }
        }

        //关闭
        table.close();
    }

    @Test
    public void scan() throws Exception{
        //获取table对象
        Table table = connection.getTable(TableName.valueOf("student"));
        //封装
        //查询全表数据
        Scan scan = new Scan();
        //指定查询某个列簇的数据
        //scan.addFamily("base_info".getBytes());
        //指定查询某个列的数据
        //scan.addColumn("base_info".getBytes(),"age".getBytes());
        //查询多个版本
        //scan.readVersions(2);
        //查询rowkey范围段的数据
        scan.withStartRow("1003".getBytes(),false);
        scan.withStopRow("1007".getBytes(),true);

        //查询
        ResultScanner scanner = table.getScanner(scan);
        //结果展示
        Iterator<Result> it = scanner.iterator();
        while (it.hasNext()){
            Result row = it.next();
            List<Cell> cells = row.listCells();

            for(Cell cell:cells){
                //获取rowkey
                String rowkey = Bytes.toString(CellUtil.cloneRow(cell));
                String family = Bytes.toString(CellUtil.cloneFamily(cell));
                String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
                if( family.equals("base_info") && qualifier.equals("age")){
                    int value = Bytes.toInt(CellUtil.cloneValue(cell));
                    System.out.println("rowkey="+rowkey+",family="+family+",qualifier="+qualifier+",value="+value);

                }else{

                    String value = Bytes.toString(CellUtil.cloneValue(cell));
                    System.out.println("rowkey="+rowkey+",family="+family+",qualifier="+qualifier+",value="+value);

                }

            }
        }
        //关闭
        table.close();
    }

    /**
     * 清空表
     * @throws Exception
     */
    @Test
    public void truncate() throws Exception{

        //禁用表
        admin.disableTable(TableName.valueOf("student"));
        //清空表
        admin.truncateTable(TableName.valueOf("student"),false);

        //判断表是否存在
        System.out.println(admin.tableExists(TableName.valueOf("student")));

    }

    @After
    public void close() throws Exception{
        //关闭
        admin.close();
        connection.close();
    }
}

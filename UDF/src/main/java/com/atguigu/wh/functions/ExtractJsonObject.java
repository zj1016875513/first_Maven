package com.atguigu.wh.functions;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDTF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by VULCAN on 2021/4/1
 */
public class ExtractJsonObject  extends GenericUDTF {

    /*

            ①检查入参
                    一列，string
            ②返回UDTF函数，生成的每一行对应所有列类型的ObjectInspector
                    返回的每一行，只有1列，string类型


                   argOIs：传入的参数的结构体

                   举例：   a('a',11,true)

                   argOIs = ｛'a',11,true ｝
     */
    @Override
    public StructObjectInspector initialize(ObjectInspector[] argOIs) throws UDFArgumentException {

       // List<? extends StructField> fieldRefs = argOIs.getAllStructFieldRefs();
        //检查参数的个数，如果不是1列，抛异常
        if (argOIs.length != 1){
            throw  new UDFArgumentException("只允许传入一列!");
        }

        //检查这列参数的类型，如果不是string，抛异常
        if (!"string".equals(argOIs[0].getTypeName())){
            throw new UDFArgumentException("只允许传入string类型!");
        }

        //返回 生成的一行的所有列类型的ObjectInspector
        ArrayList<String> fieldNames = new ArrayList<String>();
        ArrayList<ObjectInspector> fieldOIs = new ArrayList<ObjectInspector>();

        //返回的一行的每一列的别名
        fieldNames.add("col1");

        //返回的一行的每一列的类型检查员
        fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);

        return ObjectInspectorFactory.getStandardStructObjectInspector(fieldNames,
                fieldOIs);
    }


    //准备一个写出的容器
    String forwardObj[] = new String[1];

    /*
            args: 函数传入的参数
     */
    @Override
    public void process(Object[] args) throws HiveException {

        //从传入的参数中取出jsonStr
        String actionsStr = args[0].toString();

        // 将JsonStr 转为 Java中对应的  JsonArray对象
        JSONArray jsonArray = new JSONArray(actionsStr);

        //遍历取出 jsonArray中的每一个 JsonObject，再将JsonObject转成String，写出
        for ( int i=0 ; i< jsonArray.length() ; i++){
            String resultStr = jsonArray.getJSONObject(i).toString();
            //放入容器
            forwardObj[0] = resultStr;
            // 写出
            forward(forwardObj);
        }

    }

    @Override
    public void close() throws HiveException {

    }
}

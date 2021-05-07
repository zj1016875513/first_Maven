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

public class UDTF extends GenericUDTF {
    @Override
    public StructObjectInspector initialize(ObjectInspector[] argOIs) throws UDFArgumentException {
        if (argOIs.length!=1){
            throw new UDFArgumentException("参数只能为1");
        }
        if(!"string".equals(argOIs[0].getTypeName())){
            throw new UDFArgumentException("只允许传入string类型的参数");
        }
        ArrayList<String> fieldName = new ArrayList<String>();
        ArrayList<ObjectInspector> objectInspectors = new ArrayList<>();

        fieldName.add("col1");
        objectInspectors.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);

        return ObjectInspectorFactory.getStandardStructObjectInspector(fieldName,objectInspectors);
    }

    @Override
    public void process(Object[] args) throws HiveException {
        String s = args[0].toString();
        JSONArray jsonArray = new JSONArray(s);
        for (int i = 0; i < jsonArray.length(); i++) {
            String s1 = jsonArray.getJSONObject(i).toString();
            forward(s1);
        }
    }

    @Override
    public void close() throws HiveException {

    }
}

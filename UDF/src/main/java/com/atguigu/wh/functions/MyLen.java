package com.atguigu.wh.functions;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentLengthException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentTypeException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

/**
 * 该类对应的函数的功能 ：获取字符串的长度
 *
 * 自定义函数（UDF）：
 *      1.自定义类继承GenericUDF类
 *      2.重写方法
 *      3.打包
 */
public class MyLen extends GenericUDF {
    /**
     * 初始化（用来判断参数的个数和类型是否正确）
     * @param arguments
     * @return 返回结果的类型
     * @throws UDFArgumentException
     */
    public ObjectInspector initialize(ObjectInspector[] arguments) throws UDFArgumentException {
        //判断参数的个数
        if (arguments.length != 1){
            throw new UDFArgumentLengthException("args number is error!!!");
        }

        //判断参数的类型
        if (!arguments[0].getCategory().equals(ObjectInspector.Category.PRIMITIVE)){
            throw new UDFArgumentTypeException(0,"args type is error!!!");
        }
        return PrimitiveObjectInspectorFactory.javaIntObjectInspector;
    }

    /**
     * 函数功能的具体实现
     * @param arguments
     * @return
     * @throws HiveException
     */
    public Object evaluate(DeferredObject[] arguments) throws HiveException {
        //获取到传入函数的实参
        Object o = arguments[0].get();
        //返回内容的长度
        return o.toString().length();
    }

    /**
     * 回显字符（在这返回""）
     * @param children
     * @return
     */
    public String getDisplayString(String[] children) {
        return "";
    }
}

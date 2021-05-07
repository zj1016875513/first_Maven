package com.atguigu.hbase1;

import java.util.Arrays;

public   class  T1 {
    public  static char[] aaa;
    public static void main(String[] args) {
        int[] aa = {1,2,3,4,5};
        int a =10;
        char[] bb = {'a','b','c','d','e'};
        String cc = "test";
        String str1 = new String(aa, 0, 3);//因为是ASCII码，所以1,2,3会乱码
        String str2 =new String(bb,1,bb.length-1);
        System.out.println(str1);
        System.out.println(str2);
        System.out.println((char)1);
        String s = Arrays.toString(aa);
        System.out.println(s);
        aaa= new char[]{'a', 'b', 'c'};
    }
}

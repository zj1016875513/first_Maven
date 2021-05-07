package com.atguigu.teacher3.wordcount;

public class ArgsDemo {
    public static void main(String[] args) {
        for (String arg : args) {
            System.out.println(arg);
        }
        System.out.println("abc\n".length());
    }
}

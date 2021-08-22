package com.atguigu.ttest;

public class hash {
    public static void main(String[] args) {
        String[] abc ={"abc","abcd","abcde","abcdef"};
        for (String ss : abc) {
            int i = ss.hashCode();
            System.out.println(i);
        }
    }
}

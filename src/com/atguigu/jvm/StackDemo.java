package com.atguigu.jvm;

public class StackDemo {

    public static void test01(){
        System.out.println("22222222222");
    }

    public static void test02(){
        System.out.println("3333333333");
    }
    public static void main(String[] args) {
        System.out.println("111111111");
        test01();
        test02();
        System.out.println("4444444444444444444");
    }
}

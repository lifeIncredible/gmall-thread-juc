package com.atguigu.ref;

/**
 * @Author 苏晓虎
 * @Description: 强引用代码演示
 * @create 2020-04-15 1:18
 */
public class StrongReferenceDemo {
    public static void main(String[] args) {
        Object obj1 = new Object(); //这样写默认就是强引用
        Object obj2 = obj1; //obj2引用赋值
        obj1 = null; //置空
        System.gc();
        System.out.println(obj2);
    }
}

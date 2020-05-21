package com.atguigu.ref;

import java.lang.ref.WeakReference;

/**
 * @Author 苏晓虎
 * @Description: 弱引用代码演示案例
 * @create 2020-04-15 1:51
 */
public class WeakReferenceDemo {
    public static void main(String[] args) {
        Object o1 = new Object();
        WeakReference<Object> weakReference = new WeakReference<>(o1);
        System.out.println(o1);
        System.out.println(weakReference.get());

        o1 = null;
        System.gc();
        System.out.println("========================");
        System.out.println(o1);
        System.out.println(weakReference.get());
    }
}

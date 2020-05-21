package com.atguigu.ref;

import java.lang.ref.SoftReference;

/**
 * @Author 苏晓虎
 * @Description: 软引用代码案例 内存够用的时候就保留，不够用就回收！
 * @create 2020-04-15 1:29
 */
public class SoftReferenceDemo {



     //演示内存够用
    public static void softRef_Memory_Enough(){
        Object o1 = new Object();
        SoftReference<Object> softReference = new SoftReference<Object>(o1);
        System.out.println(o1);
        System.out.println(softReference.get()); //获取当前软引用对象

        o1 = null;
        System.gc(); //手动gc

        System.out.println(o1);
        System.out.println(softReference.get());
    }


    /*
     * JVM配置，故意产生大对象并配置小的内存，让它内存不够用了导致OOM，看软引用的回收情况
     * -Xms5m -Xmx5m -XX:+PrintGCDetails
     */
    public static void softRef_Memory_NotEnough(){
        Object o1 = new Object();
        SoftReference<Object> softReference = new SoftReference<Object>(o1);
        System.out.println(o1);
        System.out.println(softReference.get()); //获取当前软引用对象

        o1 = null;

        try {
           byte[] bytes = new byte[30 * 1024 *1024];
        }catch (Throwable e){
            e.printStackTrace();
        }finally {
            System.out.println(o1);
            System.out.println(softReference.get());
        }

    }

    public static void main(String[] args) {
       // softRef_Memory_Enough();
        softRef_Memory_NotEnough();
    }
}


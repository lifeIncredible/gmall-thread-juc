package com.atguigu.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 1.CAS是什么？   ===>compareAndSet
 *      比较并交换
 *  有点类似Git提交的时候版本低了，需要更新才能提交。
 *  当然此类为了演示CAS，所以没有加volatile，所以线程之间没有可见性，所以需要重新获取最新值
 */
public class CASDemo {
    public static void main(String[] args) {

        //主物理内存的值是5
        AtomicInteger atomicInteger = new AtomicInteger(5);
        //main do thing ...

        //compareAndSet(期望值，更新值)  在我修改之前期望主物理内存的值跟我期望的一样，如果一样结果为true并把主物理内存的值修改掉
        System.out.println(atomicInteger.compareAndSet(5, 2019)+"\t current data:"+ atomicInteger.get());


        //因为主物理内存的值已经被修改为2019，期望值变了，所以修改失败false，需要重新获得最新值才能修改
        System.out.println(atomicInteger.compareAndSet(5, 1024)+"\t current data:"+ atomicInteger.get());

        atomicInteger.getAndIncrement();

    }
}

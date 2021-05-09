package com.atguigu.locksupport;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author SXH
 * @create 2021-05-09 15:19
 * @description:
 */
public class LockSupportDemo {
    static Object object = new Object();

    /**
     * 现象：1、无需synchronized和lock 等代码块
     *      2、即使先unpark后park也无所谓，因为unPark方法指定了线程
     * @param args
     */
    public static void main(String[] args) {
        Thread a = new Thread(() -> {

            System.out.println(Thread.currentThread().getName() + "\t" + "-------come in");
            LockSupport.park(); //被阻塞。。。。。。。
            System.out.println(Thread.currentThread().getName() + "\t" + "------被唤醒");
        }, "A");
        a.start();

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread b = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t" + "-------come in");
            LockSupport.unpark(a);
        }, "B");
        b.start();
    }
}

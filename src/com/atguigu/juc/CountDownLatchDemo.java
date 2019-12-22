package com.atguigu.juc;

import java.util.concurrent.CountDownLatch;

/**
 * 为了控制线程的倒计时，所以我们学到了新的工具类：
 * java.util.concurrent
 *  Class CountDownLatch
 *
 *  public class CountDownLatch extends Object
 *
 */
public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 1; i <=6 ; i++) {
           new Thread(()->{
               System.out.println(Thread.currentThread().getName()+"\t 离开教室");
               countDownLatch.countDown();
           },String.valueOf(i)).start();
       }

        countDownLatch.await();
        System.out.println(Thread.currentThread().getName()+"\t  班长关门走人");


    }
}

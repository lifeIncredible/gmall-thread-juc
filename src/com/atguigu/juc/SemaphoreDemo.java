package com.atguigu.juc;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class SemaphoreDemo {
    public static void main(String[] args)
    {
        //模拟有3个车位，若只有1个 就相当于变成了synchronized，只允许一个线程进剩下等待
        Semaphore semaphore = new Semaphore(40);

        for (int i = 1; i <=6 ; i++) { //模拟6部汽车占车位
            new Thread(()->{
                boolean flag = false;
                try {
                    semaphore.acquire(); //一直等待
                    //semaphore.acquire(7);等到多久
                    flag = true;
                    System.out.println(Thread.currentThread().getName()+"\t 抢到车位");
                    //暂停几秒钟线程
                    TimeUnit.SECONDS.sleep(3);
                    System.out.println(Thread.currentThread().getName()+"\t 离开车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    if (flag)
                    {
                        semaphore.release();
                    }
                }
            },String.valueOf(i)).start();
        }
    }
}

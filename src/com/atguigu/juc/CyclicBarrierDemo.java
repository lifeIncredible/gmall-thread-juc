package com.atguigu.juc;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 线程的递增
 *  java.util.concurrent.CyclicBarrier extends Object
 * Class CyclicBarrier
 *  构造方法：
 *      CyclicBarrier(int parties)
 *      CyclicBarrier(int parties, Runnable barrierAction)
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        //CyclicBarrier(int parties, Runnable barrierAction)
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7,()->{
            System.out.println("******7颗龙珠召唤神龙");
        });
        for (int i = 1; i <=7 ; i++) {
           final  int tempI = i;
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"\t 收集到第:"+tempI+"\t 颗龙珠" );
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
        }
    }
}

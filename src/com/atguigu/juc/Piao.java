package com.atguigu.juc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/***
 * 线程   操作      资源类（高内聚、低耦合）
 * 售票练习
 */
 class Ticket{
    private static int ticket = 30;
    private Lock lock = new ReentrantLock();

    public void sale() {
        lock.lock();
        try{
            if (ticket > 0)
            {
                //currentThread()获得当前线程对象引用,getName()获取当前线程名字
                System.out.println(Thread.currentThread().getName()+"卖了一张票，剩余票数:"+(--ticket));
            }
        }catch(Exception e) {
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }

   /*public synchronized  void sale1(){
        if (ticket > 0){
            //.currentThread获得当前线程对象引用,getName()获取当前线程名字
            System.out.println(Thread.currentThread().getName()+"卖了一张票，剩余票数:"+(--ticket));
        }
    }*/

}

/**
 * 3个售票员  卖出30张票
  */
class Test01{
    public static void main(String[] args) {

        ExecutorService es = Executors.newFixedThreadPool(3);

        /*    官方API 给出的lock使用
        Lock l = ...;
        l.lock();
        try {
            // access the resource protected by this lock
        } finally {
            l.unlock();
        }
        */

        Ticket ticket = new Ticket();
        new Thread(() -> {for (int i = 1; i <=35 ; i++) ticket.sale(); },"A").start();
        new Thread(() -> {for (int i = 1; i <=35 ; i++) ticket.sale(); },"B").start();
        new Thread(() -> {for (int i = 1; i <=35 ; i++) ticket.sale(); },"C").start();



    }



}



package com.atguigu.locksupport;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author SXH
 * @create 2021-05-09 15:02
 * @description: LockSupport 前置知识复习2
 */
public class LockSupportReview2 {
    static Object object = new Object();
    static Lock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();

    /**
     * 现象：1、注释掉lock.lock 和 lock.unlock 会发现condition的await和signal方法无法使用
     *      2、调换condition的await 和signal的顺序，将A线程暂停3秒钟。B线程先执行signal唤醒，A线程出现等待
     * 结论：await和signal 必须要在同步代码块或者方法里面且成对出现使用
     *       必须先await后signal 否则无法正常唤醒
     * @param args
     */
    public static void main(String[] args) {
        new Thread(() -> {
            try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
            lock.lock();
            System.out.println(Thread.currentThread().getName() + "\t" + "--------- come in ");
            try {
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "\t" + "--------- 被唤醒");
            } finally {
                lock.unlock();
            }
        }, "A").start();


        new Thread(() -> {
            lock.lock();
            try {
                condition.signal();
                System.out.println(Thread.currentThread().getName() + "\t" + "---------通知");
            } finally {
                lock.unlock();
            }
        }, "B").start();

    }
}

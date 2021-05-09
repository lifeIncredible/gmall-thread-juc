package com.atguigu.locksupport;

import java.util.concurrent.TimeUnit;

/**
 * @author SXH
 * @create 2021-05-09 14:19
 * @description: LockSupport复习前置知识1
 */
public class LockSupportReview1 {

    public static Object object = new Object();


    /**
     * 1、若将synchronize注释掉，则wait 和 notify 无法使用。
     * 2、若将notify 和 wait 调换顺序 A线程休眠3秒钟，B线程触发唤醒。A线程进入synchronized继续永久等待
     * 3、结论：wait和notify 必须要在同步代码块或者方法里面且成对出现使用
     *        必须先wait后notify 否则无法正常唤醒
     * @param args
     */
    public static void main(String[] args) {
        new Thread(()->{
            //暂停几秒钟线程
            try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }

            synchronized (object){
                System.out.println(Thread.currentThread().getName()+"\t"+"--------come in");
                try {
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName()+"\t"+"--------被唤醒");

            },"A").start();


        new Thread(()->{
            synchronized (object){
                object.notify();
                System.out.println(Thread.currentThread().getName()+"\t"+"-----通知");
            }
        },"B").start();
    }
}

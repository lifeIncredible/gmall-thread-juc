package com.atguigu.volatilecheer;

import java.util.concurrent.atomic.AtomicInteger;

//资源类
 class Test1 {

     volatile int number = 0;

     //注意，此时number前面是volatile关键字的，不保证原子性
     public void add() {
         number++;
     }


    /**
     * Creates a new AtomicInteger with initial value {@code 0}.
     *  public AtomicInteger() {
     *     }
     */
    AtomicInteger atomicInteger = new AtomicInteger();

    public void addMyAtomic() {
        atomicInteger.getAndIncrement();
    }

 }


/**
二、 验证volatile不保证原子性
    2.1原子性指的是什么意思？
            不可分割，完整性，也即某个线程正在做某个具体业务时候，中间不可以被加塞或着被分割，需要整体完整，要么同时成功，要么同时失败。
    2.2 voltaile不保证原子性案例演示
    2.3 为什么不保证原子性？ 因为number++操作我们看来是一行代码，但是通过javap -c 看汇编是3行代码，在执行过程中丢失了数据
    2.4 如何解决原子性？
            加synchronized(杀鸡焉用牛刀)
            使用我们java.util.concurrent包下的AtomicInteger.class
            为什么加个AtomicInteger?  因为CAS
 */
public  class VolatileDemo2 {
    public static void main(String[] args) {  //一切程序的入口

        Test1 t1 = new Test1();
        for (int i = 1; i <= 20; i++) {
            new Thread(() -> {
                for (int j = 1; j <= 1000; j++) {
                    t1.add();
                    t1.addMyAtomic();
                }
            }, String.valueOf(i)).start();
        }

        //需要等待上面20个线程全部计算完成后，再用main线程取得最终结果值是多少
        while (Thread.activeCount() > 2) {
            /*Thread.activeCount()是返回活动线程的当前线程的线程组中的数量
                为什么大于2 因为默认有个main线程和GC线程在运行，只要大于2，他们就先让位
             */
            Thread.yield();
        }

        //main线程来公布结果并打印多线程环境下不使用AtomicInteger
        System.out.println(Thread.currentThread().getName() + "\t int type，finally number value:" + t1.number );

        //main线程来公布结果并打印多线程环境下使用AtomicInteger
        System.out.println(Thread.currentThread().getName() + "\t atomicInteger type ,finally number value:" + t1.atomicInteger);
    }
}

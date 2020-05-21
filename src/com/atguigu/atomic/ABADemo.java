package com.atguigu.atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author 苏晓虎
 * @create 2020-04-04 1:35
 * ABA问题解决办法？  AtomicStampedReference版本号原子引用
 */
public class ABADemo {

    //先定义一个普通的AtomicReference用于演示ABA问题
    static AtomicReference<Integer>  atomicReference = new AtomicReference<>(100);
    //带版本号的原子引用 用于演示解决ABA问题
    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100,1);

    public static void main(String[] args) {

        System.out.println("===============以下是ABA问题的产生=======================");
         new Thread(()->{
             atomicReference.compareAndSet(100,101);
             atomicReference.compareAndSet(101,100);
             },"t1").start();

          new Thread(()->{
               try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
              System.out.println(atomicReference.compareAndSet(100, 2019)+ "\t"+ atomicReference.get().toString());
          },"t2").start();



          //为了不与上面的线程互相影响，暂定一会儿
        try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
        System.out.println("===============以下是ABA问题的解决=======================");

         new Thread(()->{
             int stamp = atomicStampedReference.getStamp();
             System.out.println(Thread.currentThread().getName()+"\t第一次版本号:"+stamp);

                //暂停t3线程一秒钟
              try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
              atomicStampedReference.compareAndSet(100,101,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1);
              System.out.println(Thread.currentThread().getName()+"\t第二次版本号:"+atomicStampedReference.getStamp());

             atomicStampedReference.compareAndSet(101,100,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1);
             System.out.println(Thread.currentThread().getName()+"\t第三次版本号:"+atomicStampedReference.getStamp());

              },"t3").start();

         new Thread(()->{
                 int stamp = atomicStampedReference.getStamp();
                 System.out.println(Thread.currentThread().getName()+"\t第一次版本号:"+stamp);

                 //暂停3秒钟 t4线程，保证上面的t3线程完成一次ABA操作。
                 try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
                 boolean result = atomicStampedReference.compareAndSet(100, 2019, stamp, stamp + 1);

                 System.out.println(Thread.currentThread().getName()+"\t 修改成功否: "+result+"\t当前最新实际版本号:" + atomicStampedReference.getStamp());

                 System.out.println(Thread.currentThread().getName()+"\t 当前最新值: "+atomicStampedReference.getReference());
                 },"t4").start();

    }


}

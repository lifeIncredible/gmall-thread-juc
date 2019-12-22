package com.atguigu.volatilecheer;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class  MyData{
//为什么加了volatile 就会有可见性？？？？？？？？？？
   volatile int  number = 0;

    public void  addTo60(){
        this.number = 60;
    }

    //注意，此时number前面是volatile关键字的，不保证原子性
   public   void addPlus(){
        number++;
    }

    AtomicInteger atomicInteger = new AtomicInteger();
    public  void  addMyAtomic(){
        atomicInteger.getAndIncrement();
    }
}



/**
 * 1 验证volatile的可见性
 *          1.1假如 int number = 0 ; number变量之前根本没有添加volatile关键字修饰
 *          1.2 在定义number时加上volatile关键字，main线程收到A线程工作内存中修改的数据
 *  2. 验证volatile不保证原子性
 *       2.1原子性指的是什么意思？
 *              不可分割，完整性，也即某个线程正在做某个具体业务时候，中间不可以被加塞或着被分割，
 *              需要整体完整，要么同时成功，要么同时失败。
 *       2.2 voltaile不保证原子性案例演示
 *
 *       2.3   why
 *       2.4 如何解决原子性？
 *              加synchronized(杀鸡焉用牛刀)
 *              使用我们juc 下AtomicInteger
 *                  为什么加个AtomicInteger?
 */
public class VolatileDemo {
    public static void main(String[] args) {
            MyData myData = new MyData();
            for (int i = 1; i <=20 ; i++) {
                new Thread(()->{
                    for (int j = 1; j <=1000 ; j++) {
                        myData.addPlus();
                        myData.addMyAtomic();
                    }
                },String.valueOf(i)).start();
            }

       //需要等待上面20个线程全部计算完成后，再用main线程取得最终结果值是多少
        while (Thread.activeCount()>2){
            /*Thread.activeCount()是返回活动线程的当前线程的线程组中的数量
                为什么大于2 因为默认有个main线程和GC线程在运行，只要大于2，他们就先让位
             */
            Thread.yield();
        }

        System.out.println(Thread.currentThread().getName()+"\t finally number value:"+myData.number);
        System.out.println(Thread.currentThread().getName()+"\t finally number value:"+myData.atomicInteger);
    }

    //volatile 可以保证可见性，及时通知其他线程，主物理内存的值已经被修改
    private static void seeOkByVolatile() {
        MyData myData = new MyData();

        //线程A 修改number 为60
        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"\t  come in .......");
            try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
            myData.addTo60();
            System.out.println(Thread.currentThread().getName()+"\t  updata number 60");
        },"A").start();

        while(myData.number == 0)
        {
               //main线程一直在这里等待循环，直到number值不再等于0
        }
        System.out.println(Thread.currentThread().getName()+"\t  mission is over" );
    }
}

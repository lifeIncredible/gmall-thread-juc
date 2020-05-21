package com.atguigu.volatilecheer;

import java.util.concurrent.TimeUnit;


/**
 * 此类是为了验证volatile遵守了JMM规范的可见性
 为了验证volatile 可以保证可见性，及时通知其他线程，主物理内存的值已经被修改
 * MyData就相当于主内存，有个number初始值为0
 * 有两个线程分别对这个值进行copy修改写入到主内存
 * 只要有一个线程调用addTo60（）方法就把number从0改为60
 */
class MyData {
    //为什么加了volatile 就会有可见性？？？？？？？？？？
    volatile int number = 0;

    public void addTo60() {
        this.number = 60;
    }


}


/**
 * 一、 验证volatile保证可见性
 * 1.1假如 int number = 0 ; number变量之前根本没有添加volatile关键字修饰
 * 1.2 在定义number时加上volatile关键字，main线程收到A线程工作内存中修改的数据
 */
public class VolatileDemo {
    public static void main(String[] args) {  //一切程序的入口
        MyData myData = new MyData(); //资源类


        //线程A 修改number 为60
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t  come in .......");

            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myData.addTo60();


            System.out.println(Thread.currentThread().getName() + "\t  updata number 60");
        }, "A").start();



        while (myData.number == 0) {
            //main线程一直在这里等待循环，直到number值不再等于0
        }


        System.out.println(Thread.currentThread().getName() + "\t  mission is over");
    }

}

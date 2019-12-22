package com.atguigu.volatilecheer;

/**
 *此类用于验证单例模式(懒汉式)在多线程环境下不安全！！！
 */
public class SingletonDemo {
    private  static volatile   SingletonDemo singletonDemo;
    private  SingletonDemo(){
        System.out.println(Thread.currentThread().getName()+"\t 我是构造方法SingletonDemo()");
    }

    //加synchronize可以解决但是这样是锁了整个方法
    //DCL模式  (Double Check Lock 双端检锁机制)+ volatile(必须加，不然由于指令重排，可能会使对象还没初始化完成就返回了，就会出现异常)
    public static SingletonDemo getSingletonDemo(){
        if (singletonDemo == null){
            synchronized (SingletonDemo.class){
                if (singletonDemo == null){
                    singletonDemo = new SingletonDemo();
                }
            }

        }
            return  singletonDemo;
    }

    /**
     *  ==比较内存地址
     * @param args
     */
    public static void main(String[] args) {
        //单线程（main线程的操作动作....）
       /* System.out.println(SingletonDemo.getSingletonDemo() == SingletonDemo.getSingletonDemo());
        System.out.println(SingletonDemo.getSingletonDemo() == SingletonDemo.getSingletonDemo());
        System.out.println(SingletonDemo.getSingletonDemo() == SingletonDemo.getSingletonDemo());*/


        //并发多线程后，情况发生了很大的变化

        for (int i = 1; i <=10 ; i++)
        {
            new Thread(()->{
                SingletonDemo.getSingletonDemo();
            },String.valueOf(i)).start();
        }
    }
}

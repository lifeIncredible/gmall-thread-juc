package com.atguigu.juc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

class  MyThread2 implements Runnable{

    @Override
    public void run() {

    }
}

class MyThread implements Callable<Integer>{

    @Override
    public Integer call() throws Exception {
        System.out.println("* * * * * *come in call");
        return 1024;
    }
}

/**
  线程的第三种创建方式：
           Interface Callable<V>
 一、       Callable与Runnable不同之处有：
                                  1.Callable有返回值，Runnable没有返回值
                                  2.Callable的方法名(call) 跟 Runnable的方法名(run)不同
                                  3.Callable的方法会抛异常
                                  4.Callable有泛型，返回值跟泛型必须一致。

 二、   出现的问题：
                 在new Thread()时，Thread的构造方法里没有跟Callable相关的，最多只有 new Thread(Runnable tagle,String name)
                那么既然Callable传不了，是不是就可以传一个既实现Runnable接口又实现Callable接口的实现类进去，当做转换器。
                从Api中查询发现Runnbale接口的子接口有两个：
                                     （已知的所有实现类）All Known Subinterfaces:
                                                            RunnableFuture<V>, RunnableScheduledFuture<V>
                而RunnableFuture<V>接口又有一个FutureTask实现类，从FutureTask实现类的构造方法中发现FutureTask(Callable<V> callable)
                All Implemented Interfaces(所有已实现的接口):
                                             Runnable, Future<V>, RunnableFuture<V>  所以FutureTask可以当做转换器使用！！！！！


 */
public class CallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        FutureTask futureTask = new FutureTask(new MyThread());

         new Thread(futureTask,"A").start();

        System.out.println(futureTask.get());
    }
}

package com.atguigu.juc;

import java.util.concurrent.*;

/**
 * 线程池的优势：
 *          线程池做的工作只要是控制运行的线程数量，处理过程中将任务放入队列，然后在线程创建后启动这些任务，
 *          如果线程数量超过了最大数量，超出数量的线程排队等候，等其他线程执行完毕，再从队列中取出任务来执行。
 *
 * 它的主要特点为：线程复用;控制最大并发数;管理线程。

        第一：降低资源消耗。通过重复利用已创建的线程降低线程创建和销毁造成的销耗。
        第二：提高响应速度。当任务到达时，任务可以不需要等待线程创建就能立即执行。
        第三：提高线程的可管理性。线程是稀缺资源，如果无限制的创建，不仅会销耗系统资源，还会降低系统的稳定性，使用线程池可以进行统一的分配，调优和监控。

 架构说明：
        Java中的线程池是通过Executor框架实现的，该框架中用到了Executor（接口），Executors(线程工具类)，ExecutorService(子接口)，ThreadPoolExecutor(线程池)这几个类

 这三个线程池都有巨坑，不能使用。需要自定义线程池

 * 题目：
 *  一个银行已经new好了5个受理窗口，有5个工作人员。
 *  一池5个工作人员(银行受理窗口),模拟20个客户(request)来银行实体店办理业务。
 */
public class ThreadPoolDemo {
    public static void main(String[] args) {
        // ExecutorService threadPool = Executors.newFixedThreadPool(5);  //一池固定线程数
        //ExecutorService threadPool2 = Executors.newSingleThreadExecutor(); //一池一线程
         ExecutorService threadPool3 = Executors.newCachedThreadPool();//一池N线程，会根据需要创建新线程。可扩容，遇强则强。

        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                2,
                5,
                1L,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<Runnable>(3),
                Executors.defaultThreadFactory(),new ThreadPoolExecutor.DiscardPolicy()
        );
/*四种拒绝策略：new ThreadPoolExecutor.AbortPolicy()默认
                new ThreadPoolExecutor.CallerRunsPolicy() 原路返回
                new ThreadPoolExecutor.DiscardOldestPolicy()抛弃队列中等待最久的任务，然后把当前任务加人队列中尝试再次提交当前任务。
                new ThreadPoolExecutor.DiscardPolicy() 挤不进来的丢掉
*/
//最多支持8个，实际工作中不允许出现线程池承受最大，9个可能会因为cpu的调度不会报错，也可能会报java.util.concurrent.RejectedExecutionException
        for (int i = 1; i <=10 ; i++) {
            final  int tempI = i;
            threadPool.execute(()->{
                System.out.println(Thread.currentThread().getName()+"\t 受理业务"+"\t 客户号:"+tempI);
            });
        }

    }
}

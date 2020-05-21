package com.atguigu.juc;

        import javafx.beans.binding.When;
        import org.omg.SendingContext.RunTime;

        import java.sql.SQLOutput;
        import java.util.concurrent.ArrayBlockingQueue;
        import java.util.concurrent.BlockingQueue;
        import java.util.concurrent.TimeUnit;
        import java.util.concurrent.atomic.AtomicInteger;
        import java.util.jar.JarEntry;

/**
 * @author sxh
 * @create 2020-04-05 13:28
 * 线程通信之生产者消费者阻塞队列版
 */
class MyResource {

    private volatile boolean FLAG = true;  //默认开启，进行生产 + 消费
    private AtomicInteger atomicInteger = new AtomicInteger();
    BlockingQueue<String> blockingQueue = null;

    //构造方法  不能写具体用哪个队列,要学习spring 的依赖注入通过构造方法赋值或者setter方法
    public MyResource(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
        System.out.println(blockingQueue.getClass().getName());
    }

    //生产者
    public void myProd() throws Exception {
        String data = null;
        boolean retValue;
        while (FLAG) {
            data = atomicInteger.incrementAndGet() + "";
            retValue = blockingQueue.offer(data, 2, TimeUnit.SECONDS);
            if (retValue) {
                System.out.println(Thread.currentThread().getName() + "\t 插入队列" + data + "成功");
            } else {
                System.out.println(Thread.currentThread().getName() + "\t 插入队列" + data + "失败");
            }
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println(Thread.currentThread().getName() + "\t 大老板叫停了，表示FLAG = false ,生产动作结束");
    }

    //消费者
    public void myConsumer() throws Exception {

        String result = null;
        while (FLAG) {
            result = blockingQueue.poll(2L, TimeUnit.SECONDS);
            if (null == result || result.equalsIgnoreCase("")) {
                FLAG = false;
                System.out.println(Thread.currentThread().getName() + "\t 超过2秒没有取到蛋糕，消费者退出");
                return;
            }
            System.out.println(Thread.currentThread().getName() + "\t 消费队列消费掉蛋糕" + result + "成功");
        }
    }

    public void stop() {
        this.FLAG = false;
    }
}

public class ProdConsumer_BlockQueueDemo {
    public static void main(String[] args) throws Exception {
        MyResource myResource = new MyResource(new ArrayBlockingQueue<>(10));

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t 生产者线程启动");
            try {
                myResource.myProd();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "Prod").start();


        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t 消费者线程启动");
            System.out.println();
            System.out.println();
            try {
                myResource.myConsumer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "Consumer").start();


        try { TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) { e.printStackTrace(); }

        System.out.println();
        System.out.println();
        System.out.println();

        System.out.println("5秒钟时间到，大老板main线程叫停，活动结束");

        myResource.stop();

    }


}
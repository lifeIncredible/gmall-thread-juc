package com.atguigu.locksupport;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

/**
 * @Author 苏晓虎
 * @create 2020-08-13 16:19
 * @Description: 这是一个先进先出的非重入锁代码案例
 */
public class FIFOMutex_LockSupport {

    private final AtomicBoolean locked = new AtomicBoolean(false);//使用给定的初始值创建一个新的AtomicBoolean。
    private final Queue<Thread> waiters = new ConcurrentLinkedDeque<Thread>();
    /*
    底层是一个静态内部类static final class Node<E>,调用这个静态内部类的构造方法如下：
      public ConcurrentLinkedDeque() {
        head = tail = new Node<E>(null);
    }
    继续深入：
    Node(E item) {
         UNSAFE.putObject(this, itemOffset, item);
    }
    此unsafe类native方法的作用如下：  (这可不能忘啊！！！！)
    为给定地址设置值，忽略修饰限定符的访问限制，与此类似操作还有 :
    putInt,putDouble，
    putLong， putChar 等
    */

    public void lock(){

        boolean wasInterrupted = false;

        //返回当前执行线程对象的引用，这tm竟然忘了！！！
        Thread currentThread = Thread.currentThread();
        waiters.add(currentThread);

        //peek方法返回队列中的第一个，如果为空怎么返回null。  cas修改值
        while (waiters.peek() != currentThread || locked.compareAndSet(false,true)){
            LockSupport.park(this);

            //测试当前线程是否被中断，中断返回true
            if (Thread.interrupted()){
                wasInterrupted = true;
            }
        }

        waiters.remove();  //清空队列
        //如果为true，说明线程被park方法阻断处于阻塞状态
        if (wasInterrupted){
            //告知线程退出阻塞状态
            currentThread.interrupt();
        }
    }

    public void unLock(){
        locked.set(false);
        LockSupport.unpark(waiters.peek());
    }

}

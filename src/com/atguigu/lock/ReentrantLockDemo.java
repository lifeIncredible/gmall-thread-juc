package com.atguigu.lock;

        import java.util.concurrent.TimeUnit;
        import java.util.concurrent.locks.Lock;
        import java.util.concurrent.locks.ReentrantLock;

class Phone implements Runnable
{
    public synchronized void sendSMS()throws  Exception{
        System.out.println(Thread.currentThread().getName()+"\t invoked sendSMS()");
        sendEmail();
    }

    public synchronized void sendEmail()throws  Exception{
        System.out.println(Thread.currentThread().getName()+"\t invoked ********sendEmail()");
    }

    // =====================================================
    Lock lock = new ReentrantLock();
    @Override
    public void run() {
        get();
    }

    public void get(){
        lock.lock();
        try{
            System.out.println(Thread.currentThread().getName()+"\t invoked get()");
            set();
        }finally{
            lock.unlock();
        }
    }


    public void set(){
        lock.lock();
        try{
            System.out.println(Thread.currentThread().getName()+"\t invoked set()");
        }finally{
            lock.unlock();
        }
    }

}

/**
 * @author sxh
 * @create 2020-04-04 3:29
 * 可重入锁(也叫递归锁)
 *  例子1 证明了synchronize的是典型的可重入锁
 * t1	 invoked sendSMS()
 * t1	 invoked ********sendEmail()
 * t2	 invoked sendSMS()
 * t2	 invoked ********sendEmail()
例子2  证明ReentrantLock是典型的可重入锁
 */
public class ReentrantLockDemo {

    public static void main(String[] args) {

        Phone phone = new Phone();

       new Thread(()->{
            try {
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"t1").start();


        new Thread(()->{
            try {
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"t2").start();



        //========================例子二======================================
        try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();

        Thread t3 = new Thread(phone,"t3");
        Thread t4 = new Thread(phone,"t4");

        t3.start();
        t4.start();
    }
}

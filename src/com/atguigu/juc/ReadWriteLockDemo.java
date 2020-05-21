package com.atguigu.juc;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 资源类  读  写
 * volatile  1.保证可见性 2.不保证原子性 3.禁止指令重排序、
 */
class MyCache {
    private volatile Map<String, String> map = new HashMap<>();
    private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

     //最终版本加读写锁
    public void put(String key, String value) {
        rwl.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t写入开始");
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + "\t写入结束");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rwl.writeLock().unlock();
        }

    }

    public void get(String key) {
        rwl.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t 读取开始");
            String result = map.get(key);
            System.out.println(Thread.currentThread().getName() + "\t 读取结束" + result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rwl.readLock().unlock();
        }
    }
}
    /*  第二种版本加lock 锁

    private Lock lock = new ReentrantLock();
    public  void put(String key,String value)
    {
        lock.lock();
        try{
            System.out.println(Thread.currentThread().getName()+"\t写入开始");
            map.put(key,value);
            System.out.println(Thread.currentThread().getName()+"\t写入结束");
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }

    }

    public void get(String key)
    {
        lock.lock();
        try{
            System.out.println(Thread.currentThread().getName()+"\t 读取开始");
            String result = map.get(key);
            System.out.println(Thread.currentThread().getName()+"\t 读取结束"+result);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }

    }*/


/**
 * 官方API 不允许质疑！！！！！！！！！
 * 读写锁接口： ReadWriteLock
 *          实现类:    java.util.concurrent.locks.ReentrantReadWriteLock       Class ReentrantReadWriteLock
 *              final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
 *              rwl.readLock().lock();  rwl.readLock().unlock();
 *              rwl.writeLock().lock(); rwl.writeLock().unlock();
 *   ------------------------------------------------------------------------------------------
 * 多个线程同时读一个资源类没有任何问题，所以为了满足并发量，读取共享资源应该可以同时进行。
 * 但是
 * 如果有一个线程想去写共享资源，就不应该再有其他线程可以对该进行读或写
 *-----------------------------------------------------------------------------------------------
 * 总结：
 *      读-读能共存
 *      读-写 不能共存
 *      写-写 不能共存
 *------------------------------------------------------------------------------------------
    演示：
        1. 不加锁  写入错乱，并发读可以。
        2.  加lock锁  写数据一致，但是并发读下降(牺牲了并发性，保证了安全性,一读一写可以用lock)
        3.  加ReentrantReadWriteLock  写唯一，读并发高性能
 */
public class ReadWriteLockDemo {
    public static void main(String[] args) {

        MyCache myCache = new MyCache();

        for (int i = 1; i <= 10; i++) {
            final int tempI = i;
            new Thread(() -> {
                //Lambda表达式语法规定不能使用循环的变量,解决办法：复制“i”到有效的最终临时变量
                myCache.put(tempI + "", tempI + "");
            }, String.valueOf(i)).start();
        }

        for (int i = 1; i <= 10; i++) {
            final int tempI = i;
            new Thread(() -> {
                //Lambda表达式语法规定不能使用循环的变量,解决办法：复制“i”到有效的最终临时变量
                myCache.get(tempI + "");
            }, String.valueOf(i)).start();
        }

    }

}
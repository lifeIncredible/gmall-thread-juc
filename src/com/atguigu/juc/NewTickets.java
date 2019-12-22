package com.atguigu.juc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Tickets{
    private int number =30;
    private Lock lock = new ReentrantLock();

    public void sale(){
        lock.lock();
        try{
            if (number>0){
                System.out.println(Thread.currentThread().getName()+"\t 卖了张票剩余:"+(--number));
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }
}
public class NewTickets {
    public static void main(String[] args) {
        Tickets tickets = new Tickets();
        ExecutorService executor = Executors.newFixedThreadPool(3);
        for (int i = 1; i <=30 ; i++) {
            executor.execute(()->{
                tickets.sale();
            });
        }
        executor.shutdown();

    }

}

package com.atguigu.juc;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueDemo {
    public static void main(String[] args) throws InterruptedException {

        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);

        /**
         * 4.超时退出   offer(e,time,unit)插入     poll(time,unit)移出
         */
        /*
        System.out.println(blockingQueue.offer("a",3L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("b",3L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("c",3L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("d",3L, TimeUnit.SECONDS));

        */





        /**
         * 3.阻塞:
         *      插入: put(e)  移出: take()
          *当阻塞队列满时，生产者线程继续往队列里put元素，队列会一直阻塞生产者线程直到put数据or响应中断退出
         * 当阻塞队列空时，消费者线程试图从队列里take元素，队列会一直阻塞消费者线程直到队列可用
         */

        /*
        blockingQueue.put("a");
        blockingQueue.put("b");
        blockingQueue.put("c");
        System.out.println("11111111111111111111111");
        blockingQueue.put("d");
        System.out.println("222222222222222222222222");
        blockingQueue.take();
        blockingQueue.take();
        blockingQueue.take();
        blockingQueue.take();
        System.out.println("44444444444444");
        */








        /**
        2.特殊值 ：
                添加--offer(e)  移出---poll()  检查-----peek()
         插入方法，成功ture失败false
         移除方法，成功返回出队列的元素，队列里没有就返回null

         * */

        /*System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer("c"));
        System.out.println(blockingQueue.offer("d"));*/

        /*System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
*/


        /**1.抛出异常:
                [插入add()  移出remove()  检查element（） 这三个方法返回值都是boolean]

            当阻塞队列满时，再往队列里add插入元素会抛IllegalStateException:Queue full
           当阻塞队列空时，再往队列里remove移除元素会抛NoSuchElementException
*/
        //java.lang.IllegalStateException: Queue full
       /* System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));*/
       // System.out.println(blockingQueue.add("x"));

        //因为是队列先进先出，所以只能取值第一个
      //  System.out.println(blockingQueue.element());
        /*
                java.util.NoSuchElementException
         System.out.println(blockingQueue.remove());
         System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
*/
    }
}

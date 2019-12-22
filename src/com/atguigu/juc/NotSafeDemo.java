package com.atguigu.juc;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 1 故障现象
 *  java.util.ConcurrentModificationException
 *
 *  2 导致原因
 *      没加锁
 *  *      toString遍历读写不一致if（modCount != expectedModCount）
 *  3 解决办法
 *      3.1 Vector();
 *      3.2 Collections.synchronizedList(new ArrayList<>());
 *      3.3 new CopyOnWriteArrayList<>(); 写时复制技术
 *
 *  4 优化建议
 *
 */
public class NotSafeDemo {
    public static void main(String[] args) {

        List<String> list = new CopyOnWriteArrayList<>();//Collections.synchronizedList(new ArrayList<>()); //new Vector<>(); //new ArrayList<String>();

       for (int i = 1; i <=10 ; i++) {
            new Thread(()->{
              list.add(UUID.randomUUID().toString().substring(0,6));
              System.out.println(list);
            },String.valueOf(i)).start();
       }
    }
}

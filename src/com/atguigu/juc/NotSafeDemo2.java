package com.atguigu.juc;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


public class NotSafeDemo2 {
    public static void main(String[] args) {
        //HashMap<>() 线程不安全也会出现  java.util.ConcurrentModificationException

        Map<String,String> map = new ConcurrentHashMap<>();//Collections.synchronizedMap(new HashMap<>());//new HashMap<>();
        for (int i = 1; i <=60 ; i++) {
            new Thread(()->{
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0,6));
                System.out.println(map);
                },String.valueOf(i)).start();
        }


        /*
            因为线程不安全出现的共同错误:java.util.ConcurrentModificationException

        Set<String> set = new CopyOnWriteArraySet<>(); //Collections.synchronizedSet(new HashSet<>()); //new HashSet<>();
          for (int i = 1; i <=30 ; i++) {
              new Thread(()->{
                    set.add(UUID.randomUUID().toString().substring(0,6));
                  System.out.println(set);
              },String.valueOf(i)).start();
          }*/


        /*
         *  通过new HashSet<>().add("a"); 分析发现:
         HashSet底层 ：
         是一个hashMap,因为HashSet不安全而底层又是HashMap<>()所以HashMap也不安全

         public HashSet() {
         map = new HashMap<>();
         }

         .add()方法底层：
         实际上是一添加了一个map.put() 方法,也就是说add(参数) 就是map.put()方法的key值
         而PRESENT底层是一个Object类型常量   private static final Object PRESENT = new Object();

         public boolean add(E e) {
         return map.put(e, PRESENT)==null;
         }
         */
    }
}

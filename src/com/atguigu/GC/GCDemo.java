package com.atguigu.GC;

import java.util.Random;

/**
 * @Author 苏晓虎
 * @Description: 用于演示7大GC收集器
 * @create 2020-04-18 13:40
 *  1
 *    -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseSerialGC       (DefNew+Tenured)
 *  2
 *    -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseParNewGC       (ParNew+Tenured)
 *    备注情况：
 *      Java HotSpot(TM) 64-Bit Server VM warning: Using the ParNew young collector
 *      with the Serial old collector is deprecated and will likely be removed in a future release
 *
 *  3
 *    -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseParallelGC      (PSYoungGen+ParOldGen)
 *
 *  4
 *   4.1
 *   -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseParallelOldGC   (PSYoungGen+ParOldGen)
 *   4.2 不加就是默认UseParallelGC
 *   -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags     (PSYoungGen+ParOldGen)
 *
 *   5
 *    -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseConcMarkSweepGC    （par new generation + Concurrent Mark Sweep）
 *
 *   6
 *    -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseG1GC   单独讲G1
 *
 *   7(理论知道即可，实际中java8已经被优化掉了，没有了。)
 *   -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseSerialOldGC
 */
public class GCDemo {

    public static void main(String[] args) {

        System.out.println("********GCDemo hello");
        try {
            String str = "atguigu";
            while (true){
                str += str + new Random().nextInt(777777777)+ new Random().nextInt(888888888);
                str.intern();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

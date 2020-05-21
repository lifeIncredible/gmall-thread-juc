package com.atguigu.oom;

import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

/**
 * @Author 苏晓虎
 * @Description: 演示 java.lang.OutOfMemoryError：Direct buffer memory
 * @create 2020-04-15 12:03
 */
public class DirectBufferMemoryDemo {
    public static void main(String[] args) {
        System.out.println("配置的maxDirectMemory:"+( sun.misc.VM.maxDirectMemory()/(double)1024 /1024 )+"MB");

         try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }

         // -XX:MaxDirectMemorySize=5m  我们配置为5MB，但实际使用6MB，故意使坏
         ByteBuffer bb = ByteBuffer.allocateDirect(6 * 1024 * 1024);

    }
}

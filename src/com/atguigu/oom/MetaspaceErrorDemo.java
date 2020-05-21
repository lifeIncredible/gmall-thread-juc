package com.atguigu.oom;

import com.sun.deploy.security.EnhancedJarVerifier;

/**
 * @Author 苏晓虎
 * @Description: 此类是演示java.lang.OutOfMemoryError: Metaspace
 *               模拟Metaspace空间溢出，我们不断生产类往元空间灌，类占据的空间总是会超过Metaspace指定的空间大小的
 * @create 2020-04-15 13:59
 */
public class MetaspaceErrorDemo {

    //静态内部类
    static  class OOMTest{ }

    public static void main(String[] args) {
        int i = 0; //模拟计数多少次后发生异常
        try {
            while (true){
                i++;
                
            }
        }catch (Throwable e){
            System.out.println("**************多少次后发生了异常"+i);
            e.printStackTrace();
        }
    }
}

package com.atguigu.oom;

        import java.util.concurrent.TimeUnit;

/**
 * @Author 苏晓虎
 * @Description: 演示java.lang.OutOfMemoryError: unable to create new native thread   是在生产环境(Linux)
 * @create 2020-04-15 13:12
 */
public class UnableCreateNewThreadDemo {
    public static void main(String[] args) {
        for (int i = 1; ;i++){

            System.out.println("*************"+ i);

            new Thread(()->{
                try { TimeUnit.SECONDS.sleep(Integer.MAX_VALUE); } catch (InterruptedException e) { e.printStackTrace(); }
            },String.valueOf(i)).start();
        }
    }
}

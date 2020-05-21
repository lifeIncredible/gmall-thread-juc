package com.atguigu.oom;

        import java.util.ArrayList;

/**
 * @Author 苏晓虎
 * @Description: 演示 OutOfMemoryError: GC Overhead limit exceeded
 * @create 2020-04-15 3:23
 */
public class GCOverheadDemo {
    public static void main(String[] args) {
        int i = 0;
        ArrayList<Object> list = new ArrayList<>();

        try {
            while (true) {
                list.add(String.valueOf(++i).intern());
            }
        } catch (Exception e) {
            System.out.println("*************i:" + i);
            e.printStackTrace();
            throw e;
        }
    }
}

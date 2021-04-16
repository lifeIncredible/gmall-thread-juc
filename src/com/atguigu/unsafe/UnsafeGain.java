package com.atguigu.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;


/**
 * @Author 苏晓虎
 * @create 2020-08-13 14:04
 * @Description:获取unsafe类
 */
public class UnsafeGain {
    public static Unsafe reflectGetUnsafe() {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return (Unsafe) field.get(null);
        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) throws InstantiationException {
        Unsafe unsafe = UnsafeGain.reflectGetUnsafe();
         // 一些内存简要信息的方法
        int addressSize = unsafe.addressSize();
        int pageSize = unsafe.pageSize();

        //操作对象
        Object o = unsafe.allocateInstance(null);
        long fieldOffset = unsafe.objectFieldOffset(null);//对象属性内存偏移量

        //提供用于类和静态字段操作的方法。
        long l = unsafe.staticFieldOffset(null);
        Class<?> defineClass = unsafe.defineClass("", null, 0, 0, null, null);

        //操作数组
        int arrayBaseOffset = unsafe.arrayBaseOffset(defineClass);
        int arrayIndexScale = unsafe.arrayIndexScale(defineClass);

        //同步 用于同步的低级原语
        unsafe.monitorEnter(new Object());
        unsafe.tryMonitorEnter(new Object());
        unsafe.monitorExit(new Object());
        unsafe.compareAndSwapInt(null,0,0,0);
        unsafe.putOrderedInt(null,0,0);

        //直接内存访问方法
        unsafe.allocateMemory(100);
        unsafe.copyMemory(0,1,1);
        unsafe.freeMemory(10);
        unsafe.getAddress(1);
        unsafe.getInt(1);
        unsafe.putInt(0,1);
    }
}

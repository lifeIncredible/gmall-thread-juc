package com.atguigu.unsafe;

import sun.misc.Unsafe;

/**
 * @Author: 苏晓虎
 * @Create: 2020-10-09 14:40
 * @Description: 避免初始化,破坏单例
 */
public class AvoidingInitialization {
    private long a;

    public AvoidingInitialization(){
        this.a = 1;
    }

    public long a(){
        return this.a;
    }

    //使用构造函数,反射和不安全实例它会得到不同的结果。单例模式被破坏
    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        AvoidingInitialization o1 = new AvoidingInitialization();
        System.out.println(o1.a());

        AvoidingInitialization o2 = AvoidingInitialization.class.newInstance();
        System.out.println(o2.a());

        Unsafe unsafe = UnsafeGain.reflectGetUnsafe();
        AvoidingInitialization o3 = (AvoidingInitialization)unsafe.allocateInstance(AvoidingInitialization.class); //不允许初始化校验
        System.out.println(o3.a());
    }
}

package com.atguigu.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;


/**
 * @Author: 苏晓虎
 * @Create: 2020-10-09 15:42
 * @Description: 隐藏密码
 */
public class HidePassword {

    private static long normalize(int value) {
        if(value >= 0) return value;
        return (~0L >>> 32) & value;
    }

    static long toAddress(Object obj) {
        Object[] array = new Object[] {obj};
        long baseOffset = UnsafeGain.reflectGetUnsafe().arrayBaseOffset(Object[].class);
        return normalize(UnsafeGain.reflectGetUnsafe().getInt(array, baseOffset));
    }

    static Object fromAddress(long address) {
        Object[] array = new Object[] {null};
        long baseOffset = UnsafeGain.reflectGetUnsafe().arrayBaseOffset(Object[].class);
        UnsafeGain.reflectGetUnsafe().putLong(array, baseOffset, address);
        return array[0];
    }

    /**
     *  直接内存访问的另一种有趣用法Unsafe是从内存中删除不需要的对象。
     * 用于检索用户密码的大多数API的签名为byte[]或char[]。为什么要数组？
     * 完全出于安全原因，因为我们可以在不需要数组元素后将其无效。
     * 如果我们检索密码，因为String它可以像一个对象一样保存在内存中并使该字符串无效，则只需执行取消引用操作即可。
     * 在GC决定执行清除时，该对象仍在内存中。
     *
     * @param args
     */
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {

        //此技巧将创建String具有相同大小的假对象，并替换内存中的原始对象：
        Unsafe unsafe = UnsafeGain.reflectGetUnsafe();
        String password = new String("l00k@myHor$e");
        String fake = new String(password.replaceAll(".", "?"));
        System.out.println(password); // l00k@myHor$e
        System.out.println(fake); // ????????????
        unsafe.copyMemory(
                fake, 0L, null, toAddress(password), Guard.sizeOf(password));

        System.out.println(password); // ????????????
        System.out.println(fake); // ????????????

        //上面的方式感觉安全。其实这种方式不是很安全。为了真正的安全，我们需要通过反射使支持的char数组无效：
        Field stringValue = String.class.getDeclaredField("value");
        stringValue.setAccessible(true);
        char[] mem = (char[]) stringValue.get(password);
        for (int i=0; i < mem.length; i++) {
            mem[i] = '?';
        }
    }


}

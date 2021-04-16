package com.atguigu.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;

/**
 * @Author: 苏晓虎
 * @Create: 2020-10-09 14:51
 * @Description: 内存破坏 这是每个C程序员常用的。顺便说一句，其常见的安全绕过技术
 */
public class Guard {
    private int ACCESS_ALLOWED = 1;

    public boolean giveAccess(){
        return 42 == ACCESS_ALLOWED;
    }

    //遍历所有非静态字段(包括所有超级字段),获取每个字段的偏移量，找到最大值并添加填充。
    public static long sizeOf(Object o){
        Unsafe unsafe = UnsafeGain.reflectGetUnsafe();
        HashSet<Field> fields = new HashSet<>();
        Class<?> c = o.getClass();
        while (c != Object.class){
            for (Field f : c.getDeclaredFields()) { //返回一个数组
                if ((f.getModifiers() & Modifier.STATIC) == 0){
                    fields.add(f);
                }
            }
            c = c.getSuperclass();
        }

        // get offset
        long maxSize = 0;
        for (Field f : fields) {
            long offset = unsafe.objectFieldOffset(f);
            if (offset > maxSize) {
                maxSize = offset;
            }
        }
        return ((maxSize/8) + 1) * 8;   // padding
    }



    //客户端代码非常安全，可以调用 giveAccess()以检查访问规则。
    //不幸的是，对于客户而言，它总是会回报false。只有特权用户才
    //能以某种方式更改ACCESS_ALLOWED常量的值并获得访问权限。
    //实际上，这不是真的。这是代码演示它
    public static void main(String[] args) throws NoSuchFieldException {
        Guard guard = new Guard();
        System.out.println(guard.giveAccess());

        //现在，所有客户都将获得无限访问权限。
        Unsafe unsafe = UnsafeGain.reflectGetUnsafe();
        Field field = guard.getClass().getDeclaredField("ACCESS_ALLOWED");
        unsafe.putInt(guard,unsafe.objectFieldOffset(field),42); //设置属性的内存偏移量
        System.out.println(guard.giveAccess());

        //实际上，可以通过反射实现相同的功能。但有趣的是，我们可以修改任何对象，甚至是我们没有引用的对象。
        //例如，内存中的另一个Guard对象位于当前guard对象旁边。我们可以ACCESS_ALLOWED使用以下代码修改其字段
        //注意，我们没有使用任何对该对象的引用。 16是Guard32位体系结构中对象的大小。我们现在可以手动计算或使用sizeOf已定义的方法
        unsafe.putInt(guard,16 + unsafe.objectFieldOffset(field),42);
    }
}

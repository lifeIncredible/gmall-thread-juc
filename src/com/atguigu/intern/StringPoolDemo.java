package com.atguigu.intern;

/**
 * @author 苏晓虎start
 * @create 2020-10-20 23:37
 */
public class StringPoolDemo {
    public static void main(String[] args) {
        String str1 = new StringBuilder("ali").append("baba").toString();
        System.out.println(str1);
        System.out.println(str1.intern());
        System.out.println(str1 == str1.intern());

        String str2 = new StringBuilder("ja").append("va").toString();
        System.out.println(str2);
        System.out.println(str2.intern());
        System.out.println(str2 == str2.intern());
    }
}

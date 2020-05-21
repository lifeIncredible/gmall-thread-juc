package com.atguigu.test;

import org.junit.Test;

/**
 * @Author 苏晓虎
 * @Description:
 * @create 2020-05-14 11:42
 */
public class Test12 {
    int i = 5;

    public void test(){
        do {
            System.out.println(i--);
            i--;
        }while (i!=0);
    }

    @Test
    public void test2(){
       int [][][]x =new int[12][5][2];
        System.out.println(x.length);
    }

    @Test
    public void test3(){
        int i =5;
        do {
            System.out.println(i--);
            i--;
        }while (i!=0);
    }


    public static void main(String[] args) {
        java.util.Date[]dates =new java.util.Date[10];
        System.out.println(dates[0]);
    }
}

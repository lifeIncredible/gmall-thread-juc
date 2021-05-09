package com.atguigu.bytedance;

/**
 * @Author 苏晓虎
 * @Description:  你的任务是计算 ab 对 1337 取模，a 是一个正整数，b 是一个非常大的正整数且会以数组形式给出
 * @create 2020-04-13 18:33
 */
public class Scheme {
    // 判断是否大于0
    public static boolean morethanzero(int[] x) {
        for (int i = x.length - 1; i >= 0; i--) {
            if (x[i] > 0)
                return true;
        }
        return false;
    }

    //高精度除法
    public static void div(int[] x, int y) {
        int tmp = 0;
        for (int i = 0; i < x.length; i++) {
            x[i] += tmp * 10;
            tmp = x[i] % y;
            x[i] = x[i] / y;
        }
    }

    public static int superPow(int a, int[] b) {
        if (morethanzero(b) == false)
            return 1;
        a = a % 1337;
        boolean isEven = false;
        if (b[b.length - 1] % 2 == 0)
            isEven = true;
        div(b, 2);
        int result = superPow(a, b);
        result = result % 1337;
        result *= result;//result由于div分成了两部分，现在把两部分合在一起
        result = result % 1337;
        if (isEven == false) {
            result *= a;//奇数的话，再乘以a
            result = result % 1337;
        }
        return result;
    }


}

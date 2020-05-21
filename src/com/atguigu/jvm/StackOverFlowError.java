package com.atguigu.jvm;

/**
 * 此类用于演示栈内存溢出溢出
 * 只需要让方法递归即可，因为方法存在栈内存中
 * @author shkstart
 * @create 2020-01-31 20:23
 */
public class StackOverFlowError {

    public static void test01(){
        System.out.println("11111");
        test01();
    }
    //Exception in thread "main" java.lang.StackOverflowError
    // 实际上它是个错误不是异常,在java.lang.Throwable 的子类Error的子类下的VirtualMachineError 中有个子类叫StackOverflowError
    public static void main(String[] args) {
        test01();
    }
}

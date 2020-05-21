package com.atguigu.volatilecheer;

/**
 * 验证指令重排序
 */
public class InstructionReordering {
    int a = 0 ;
    boolean flag = false;

    public void  method01(){
        a = 1;
        flag = true;
    }

    public  void  method02(){
        if (flag)
        {
            a = a + 5 ;
            System.out.println("-*****************retValue:"+a);
        }
    }

    public static void main(String[] args) {
        InstructionReordering instructionReordering = new InstructionReordering();
        for (int i = 1; i <=3 ; i++) {
            new Thread(()->{
                instructionReordering.method01();
            },String.valueOf(i)).start();
        }
       for (int i = 4; i <=7 ; i++) {
           new Thread(()->{
                instructionReordering.method02();
           },String.valueOf(i)).start();
       }
    }

}

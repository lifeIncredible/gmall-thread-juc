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
         new Thread(()->{
         instructionReordering.method01();
                 },"A").start();

          new Thread(()->{
          instructionReordering.method02();
                  },"B").start();
    }

}

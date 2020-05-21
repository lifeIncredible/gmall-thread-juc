package com.atguigu.test;

import org.junit.Test;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author shkstart
 * @create 2020-03-15 16:27
 */

public class SeReview {

    /**
     * 遍历Map集合
     */
    @Test
    public void test01(){
        Map<String, Object> map = new HashMap<>();
        map.put("a",1);
        map.put("b",2);
        map.put("c",3);
        map.put("d",4);
        System.out.println(map.size());
        //返回map中所有的键值对(Map.Entry)
        Set<Map.Entry<String, Object>> entries = map.entrySet();
        for (Object object: entries){
            Map.Entry entry = (Map.Entry) object;
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }
    }



    @Test
    public void test02() throws IOException {
        File file = new File("D:/ASUS");
        FileInputStream inputStream = new FileInputStream(file);
        int i;
       while ( ( i = inputStream.read()) != -1){
           System.out.println((char)i);
       }

    }

    //数组的复制
    @Test
    public void test03(){
        int [] arr = {1,2,3,4,5};
        //希望最后arr中存储的元素情况是{5,4,3,2,1}
        int[] newArr = new int [arr.length];
        //倒着复制
        for (int i = 0; i <newArr.length ; i++) {
            newArr[i] = arr[arr.length -1 -i];
        }

        arr = newArr;

        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]+" ");
        }
    }

    //数组的复制第二种
    @Test
    public void test04(){
        int []arr ={1,2,3,4,5};
        //希望最后arr中存储的元素情况是{5,4,3,2,1}
        for (int i = 0; i < arr.length/2; i++) {//循环的次数，就是交换的次数
            int temp = arr[i];
            arr[i] = arr[arr.length -1-i];
            arr[arr.length-1-i] = temp;
        }

        for(int i=0; i<arr.length; i++){
            System.out.print(arr[i]+"  ");
        }
    }
}

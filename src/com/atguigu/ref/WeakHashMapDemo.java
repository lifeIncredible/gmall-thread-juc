package com.atguigu.ref;

        import java.util.HashMap;
        import java.util.Map;
        import java.util.WeakHashMap;

/**
 * @Author 苏晓虎
 * @Description:
 * @create 2020-04-15 2:15
 */
public class WeakHashMapDemo {
    public static void main(String[] args) {
        myHashMap();
        System.out.println("======================");
        myWeakHashMap();
    }
    private static void myHashMap() {
        Map<Integer, String> map = new HashMap<>();
        Integer key = new Integer(1);
        String value = "hashMap";
        map.put(key,value);
        System.out.println(map);

        key = null;
        System.out.println(map);

        System.gc();
        System.out.println(map+"\t" + map.size());
    }


    private static void myWeakHashMap() {

        WeakHashMap<Integer, String> map = new WeakHashMap<>();
        Integer key = new Integer(2);
        String value = "weakHashMap";

        map.put(key,value);
        System.out.println(map);

        key = null;
        System.out.println(map);

        System.gc();
        System.out.println(map+"\t" + map.size());
    }


}

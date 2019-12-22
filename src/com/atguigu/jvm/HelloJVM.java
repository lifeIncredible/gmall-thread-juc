package com.atguigu.jvm;

public class HelloJVM {
    public static void main(String[] args) {
        Object object = new Object();
        System.out.println(object.getClass().getClassLoader());//null
        HelloJVM helloJVM = new HelloJVM();
        System.out.println(helloJVM.getClass().getClassLoader().getParent().getParent());//null
        System.out.println(helloJVM.getClass().getClassLoader().getParent());//sun.misc.Launcher$ExtClassLoader@1540e19d
        System.out.println(helloJVM.getClass().getClassLoader());//sun.misc.Launcher$AppClassLoader@18b4aac2

    }
}

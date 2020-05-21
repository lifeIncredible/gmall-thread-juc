package com.atguigu.test;

/**
 * @Author 苏晓虎
 * @Description:
 * @create 2020-05-17 17:26
 */
public class Student {
    private String name;
    private int age;
    private String interest;//兴趣
    private int number;//班级编号

    public Student(String name, int age, String interest, int number) {
        this.name = name;
        this.age = age;
        this.interest = interest;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public static void main(String[] args) {
        Student student = new Student("张三", 20, "篮球", 201702);
    }
}



package com.atguigu.test;



/**
 * @author 苏晓虎
 * @create 2020-04-04 2:30
 */
public class TestTransferValue {
    public void ChangeValue1(int age){
        age = 30;
    }

    public void ChangeValue2(Person person){
        person.setPersonName("xxx");
    }
    public void ChangeValue3(String str){
        str = "xxx";
    }

    public static void main(String []args){
        TestTransferValue test = new TestTransferValue();
        int age = 20;
        test.ChangeValue1(age);
        System.out.println("age ----" +age);

        Person person = new Person("abc");
        test.ChangeValue2(person);
        System.out.println("personName -------- "+ person.getPersonName());

        String str = "abc";
        test.ChangeValue3(str);
        System.out.println("String --------" + str);
    }

}


class Person{
     public String personName;
     public int age;

    public Person(String personName) {
        this.personName = personName;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
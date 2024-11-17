package com.yushu;

public class Test {
    public static void main(String[] args) {
        Student student = new Student();
        student.setName("张三");
        System.out.println("1\t" + student.getName());
        Test test = new Test();
        test.test(student);
        System.out.println("2\t" + student.getName());
        String s = "小猫";
        System.out.println("4\t" + s);
        test.test2(s);
        System.out.println("6\t" + s);
    }

    void test(Student student) {
        student.setName("李四");
        System.out.println("3\t" + student.getName());
    }

    void test2(String s) {
        s = "小狗";
        System.out.println("5\t" + s);
    }


}

package com.yushu;

/**
 * @author 一灯架构
 * @apiNote Java传递示例
 **/
public class Demo {

    public static void main(String[] args) {
        int count = 0;
        update(count);
        System.out.println("main方法中count：" + count);
    }

    private static void update(int count) {
        count++;
        System.out.println("update方法中count：" + count);
    }

}
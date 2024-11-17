package com.yushu;


import java.util.ArrayList;
import java.util.List;

/**
 * VM参数： -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 */
public class HeapOOM {


    public static void main(String[] args) {
        List list = new ArrayList();
        //比较两个接口
//实现Runnable接口
        class MyThread1 implements Runnable {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    list.add(Math.random());
                }

            }
        }
        while (true) {
            new Thread(new MyThread1(), "AA").start();
        }
    }
}


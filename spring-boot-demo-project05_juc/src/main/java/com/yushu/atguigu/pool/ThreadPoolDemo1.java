package com.yushu.atguigu.pool;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

//演示线程池三种常用分类
public class ThreadPoolDemo1 {
    public static void main(String[] args) {
        //一池五线程
        ExecutorService threadPool = Executors.newFixedThreadPool(5); //5个窗口

        //一池一线程
//        ExecutorService threadPool = Executors.newSingleThreadExecutor(); //一个窗口

        //一池可扩容线程
//        ExecutorService threadPool = Executors.newCachedThreadPool();
        //10个顾客请求
        try {
            for (int i = 1; i <=100; i++) {
                //执行
                threadPool.execute(()->{
                    long time = System.nanoTime();
                    System.out.println(Thread.currentThread().getName()+" 办理业务\t"+time);
                });
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            //关闭
            threadPool.shutdown();
        }

    }

}

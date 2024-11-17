package com.bilibili.juc.cf;

import java.util.concurrent.*;

/**
 * @auther zzyy
 * @create 2022-01-16 16:27
 */
public class CompletableFutureBuildDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

    /*无返回值*/
        /*CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            //暂停几秒钟线程
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println(completableFuture.get());*/

        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            //暂停几秒钟线程
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello supplyAsync";
        }, threadPool);
        System.out.println(completableFuture.get());
        threadPool.shutdown();

    }
}

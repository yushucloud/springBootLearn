package com.yushu.async.task;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncService {

    @Async("customTaskExecutor")
    public void taskOne() {
        try {
            System.out.println("开始执行异步任务1");
            Thread.sleep(3000); // 模拟耗时操作
            System.out.println("异步任务1执行完成");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Async("customTaskExecutor")
    public void taskTwo() {
        try {
            System.out.println("开始执行异步任务2");
            Thread.sleep(3000); // 模拟耗时操作
            System.out.println("异步任务2执行完成");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

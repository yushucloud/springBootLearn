package com.yushu.async;

import com.yushu.async.task.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AsyncController {

    @Autowired
    private AsyncService asyncService;

    @GetMapping("/async")
    public String asyncTasks() {
        long startTime = System.currentTimeMillis();
        asyncService.taskOne();  // 异步执行任务1
        asyncService.taskTwo();  // 异步执行任务2
        long endTime = System.currentTimeMillis();
        return "异步任务已启动，总调用时间：" + (endTime - startTime) + " 毫秒";
    }
}

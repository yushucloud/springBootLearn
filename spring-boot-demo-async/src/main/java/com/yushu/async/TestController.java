package com.yushu.async;

import com.yushu.async.task.TaskFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TestController {
    @Resource
    private TaskFactory taskFactory;

    @GetMapping("/test")
    public String test() throws InterruptedException {
        taskFactory.task1();
        return "TEST";
    }
}

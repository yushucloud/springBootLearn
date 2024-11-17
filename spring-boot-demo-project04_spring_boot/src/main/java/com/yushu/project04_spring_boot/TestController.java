package com.yushu.project04_spring_boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * RestController 是 ResponseBody的组合
 * */
@RestController
public class TestController {

    @Autowired
    Person person;
    @Autowired
    private TestRetryService testRetryService;

    @RequestMapping(value = "/test01")
    public int test(int code) throws Exception {
        return testRetryService.testRetry(code);
    }

    @RequestMapping(value = "/demo")
    public int demo() throws Exception {
        return testRetryService.testRetry(0);
    }

    @RequestMapping("/test02")
    public Person test() {

        return person;
    }
}

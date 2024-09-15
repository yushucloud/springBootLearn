package com.yushu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class SpringBootDemoAsyncApplication {



    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoAsyncApplication.class, args);
    }

}


package com.songguoliang.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Description
 * @Author sgl
 * @Date 2018-06-26 10:02
 */
@EnableScheduling
@SpringBootApplication
public class SpringBootSchedulingApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootSchedulingApplication.class);
    }
}

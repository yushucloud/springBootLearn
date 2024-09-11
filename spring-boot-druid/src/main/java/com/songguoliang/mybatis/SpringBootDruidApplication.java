package com.songguoliang.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description
 * @Author sgl
 * @Date 2018-05-02 14:51
 */
@SpringBootApplication
@MapperScan("com.songguoliang.mybatis.mapper")
public class SpringBootDruidApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootDruidApplication.class, args);
    }
}

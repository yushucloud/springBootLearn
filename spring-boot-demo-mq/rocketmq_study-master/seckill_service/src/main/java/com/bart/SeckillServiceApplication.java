package com.bart;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.bart.provider.dao")
public class SeckillServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeckillServiceApplication.class, args);
	}

}

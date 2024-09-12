package com.oujiong.wechat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @Description: 启动类
 *
 * @author xub
 * @date 2019/11/11 下午3:57
 */
@EnableWebMvc
@SpringBootApplication
public class WechatApplication {

	public static void main(String[] args) {
		SpringApplication.run(WechatApplication.class, args);
	}
}

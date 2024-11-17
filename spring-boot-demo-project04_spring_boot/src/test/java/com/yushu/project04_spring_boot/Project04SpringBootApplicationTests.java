//package com.yushu.project04_spring_boot;
//
//import org.junit.jupiter.api.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//class Project04SpringBootApplicationTests {
//    @Autowired
//    Person person;
//    Logger logger = LoggerFactory.getLogger(Project04SpringBootApplicationTests.class);
//
//    @Test
//    void contextLoads() {
//        /*我们可以调整日志级别
//         * 日志智慧在这个级别以及以后的高级别生效
//         *
//         * Spirng boot 默认给我设置的是info级别的，默认只输出info级别以后的信息
//         * */
//        System.out.println(person);
//        logger.trace("这是跟踪");
//        logger.debug("这是调试信息");
//        logger.info("这是自己定义的信息");
//        logger.warn("这是警告信息");
//        logger.error("这是错误信息");//如果出现异常，可以记录日志
//    }
//
//}

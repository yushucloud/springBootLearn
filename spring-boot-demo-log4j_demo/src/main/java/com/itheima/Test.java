package com.itheima;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.helpers.LogLog;

public class Test {

    public static void main(String[] args) {
//        BasicConfigurator.configure();
        // 开启 log4j 内置日志记录
        LogLog.setInternalDebugging(false);
        Logger logger = Logger.getLogger(Test.class);
        logger.info("你好");

        for (int i = 0; i < 2; i++) {
            // 日志级别
            logger.fatal("fatal"); // 严重错误，一般会造成系统崩溃并终止运行

            logger.error("error"); // 错误信息，不会影响系统运行
            logger.warn("warn");   // 警告信息，可能会发生问题
            logger.info("info");   // 运行信息，数据连接、网络连接、IO 操作等等
            logger.debug("debug"); // 调试信息，一般在开发中使用，记录程序变量参数传递信息等等

            logger.trace("trace"); // 追踪信息，记录程序所有的流程信息
        }

    }
}

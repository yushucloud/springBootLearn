package com.yushu;

import org.apache.log4j.Logger;

public class LogTest {
    private static Logger logger = Logger.getLogger(LogTest.class);

    public static void main(String[] args) {
        logger.error("错误信息");
        logger.debug("debug信息");
        logger.info("info信息");
        logger.warn("warn信息");
        logger.error(new IllegalArgumentException("IllegalArgumentException"));

    }

}

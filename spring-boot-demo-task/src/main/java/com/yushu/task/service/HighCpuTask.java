package com.yushu.task.service;

import cn.hutool.core.date.DateUtil;
import com.yushu.task.aop.TrackExecutionTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class HighCpuTask {
    /**
     * 模拟CPU密集型任务，消耗大量CPU
     * 此方法用于演示和测试，通过大量的计算来模拟CPU密集型操作
     * 使用了TrackExecutionTime注解来监控执行时间
     *
     * @return 返回计算结果的总和，用于验证计算过程的完成情况
     */
    @TrackExecutionTime
    public long cpuIntensiveTask(long num) {
        long sum = 0;
        // 以下是模拟的复杂计算过程，使用循环累加来消耗CPU资源

        for (long i = 0; i < num; i++) {
            sum += i;  // 模拟复杂计算，逐步累加
        }
        return sum;  // 返回累加结果，标志着模拟任务的完成
    }

    public static void main(String[] args) {
        HighCpuTask task = new HighCpuTask();
        long num = Long.MAX_VALUE / 10000;
        num = 1000000000;

        for (int i = 0; i < 100; i++) {
//            long num2 = (long) (num * Math.pow(10, i));
            long num2 = 1000000000;
            long startTime = System.currentTimeMillis();
            long l = task.cpuIntensiveTask(num2);

            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            log.info("{}个数求和，执行时间：{} ms", num2, executionTime);
        }


    }

}

package com.yushu.task.job;

import cn.hutool.core.date.DateUtil;
import com.yushu.task.service.HighCpuTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class AsyncScheduledTask {

    @Autowired
    private HighCpuTask highCpuTask;

//    @Scheduled(fixedRate = 1000)

    /**
     * 按照标准时间来算，每隔 1s 执行一次
     */
    /*
    * 19:05:45.621 [main] INFO com.yushu.task.service.HighCpuTask - 10000个数求和，执行时间：0 ms
19:05:45.627 [main] INFO com.yushu.task.service.HighCpuTask - 100000个数求和，执行时间：2 ms
19:05:45.629 [main] INFO com.yushu.task.service.HighCpuTask - 1000000个数求和，执行时间：2 ms
19:05:45.632 [main] INFO com.yushu.task.service.HighCpuTask - 10000000个数求和，执行时间：3 ms
19:05:45.668 [main] INFO com.yushu.task.service.HighCpuTask - 100000000个数求和，执行时间：36 ms
19:05:46.041 [main] INFO com.yushu.task.service.HighCpuTask - 1000000000个数求和，执行时间：373 ms
19:05:49.679 [main] INFO com.yushu.task.service.HighCpuTask - 10000000000个数求和，执行时间：3638 ms
19:06:22.112 [main] INFO com.yushu.task.service.HighCpuTask - 100000000000个数求和，执行时间：32432 ms
    *
    * */
    @Async
    @Scheduled(cron = "0/1 * * * * ?")
    public void executeAsyncHighCpuTask() {
        log.info("开始执行：{}", DateUtil.formatDateTime(new Date()));
        // Long.MAX_VALUE / 10000 可以作为将来调整计算量的参考，但在此示例中未使用
        long num = 10000000000L;
//        long num = Long.MAX_VALUE / 100;

        long l = highCpuTask.cpuIntensiveTask(num);
        log.info("结束执行,计算结果：{}",  l);
    }
}

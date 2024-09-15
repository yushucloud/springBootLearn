package com.yushu.task.config;

@Service
public class ScheduledTask {

    @Autowired
    private HighCpuTask highCpuTask;

    // 每秒执行一次，模拟定时任务高频执行
    @Scheduled(fixedRate = 1000)
    public void executeHighCpuTask() {
        highCpuTask.cpuIntensiveTask();
    }
}

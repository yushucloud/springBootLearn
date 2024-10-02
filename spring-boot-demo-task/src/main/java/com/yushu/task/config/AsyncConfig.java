package com.yushu.task.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@Slf4j
public class AsyncConfig implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {


        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //此方法返回可用处理器的虚拟机的最大数量; 不小于1

        int core = Runtime.getRuntime().availableProcessors();
        core = 4;
        log.info("计算机线程数: {}", core);
        executor.setCorePoolSize(core);//设置核心线程数 核心线程数，视任务情况设置
        executor.setMaxPoolSize(core * 2 + 1);//设置最大线程数 最大线程数
        executor.setKeepAliveSeconds(3);//除核心线程外的线程存活时间 队列容量
        executor.setQueueCapacity(200);//队列容量 如果传入值大于0，底层队列使用的是LinkedBlockingQueue,否则默认使用SynchronousQueue
        executor.setThreadNamePrefix("HighCpuTaskExecutor-");//线程名称前缀
        executor.initialize();
//        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());//设置拒绝策略，抛出 RejectedExecutionException来拒绝新任务的处理。
//        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());//设置拒绝策略，使用主线程
//        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());//设置拒绝策略，直接丢弃掉
//        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());//设置拒绝策略，丢弃最早的未处理的任务请求。

        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SimpleAsyncUncaughtExceptionHandler();
    }
}

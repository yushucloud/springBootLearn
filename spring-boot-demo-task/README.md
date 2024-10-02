# Spring Boot Scheduled

在 Spring Boot 中，`@Scheduled` 注解可以用来设置定时任务，支持多种表达方式来定义规则。你可以通过使用固定频率、固定延迟或 Cron 表达式来控制任务的执行时间。下面是一些常见的使用方法：

### 1. 固定频率执行任务
使用 `fixedRate` 属性来定义任务执行的固定频率（单位为毫秒），任务会按固定时间间隔开始执行，无论上一个任务是否完成。

```java
@Scheduled(fixedRate = 5000)
public void fixedRateTask() {
    System.out.println("每5秒执行一次");
}
```

### 2. 固定延迟执行任务
使用 `fixedDelay` 属性来设置任务在上一个任务完成后经过固定延迟时间后再执行（单位为毫秒）。

```java
@Scheduled(fixedDelay = 5000)
public void fixedDelayTask() {
    System.out.println("上一个任务完成5秒后执行");
}
```

### 3. 初始延迟后执行
使用 `initialDelay` 和 `fixedRate` 或 `fixedDelay` 配合，设置任务在启动时先延迟一段时间再开始执行。

```java
@Scheduled(initialDelay = 10000, fixedRate = 5000)
public void initialDelayTask() {
    System.out.println("启动后10秒执行第一次任务，以后每5秒执行一次");
}
```

### 4. 使用 Cron 表达式
使用 `cron` 属性可以通过 Cron 表达式来精确控制任务的执行时间。Cron 表达式是由6到7个时间字段组成，用于定义秒、分、时、日、月、星期等。

```java
@Scheduled(cron = "0 0/1 * * * ?")
public void cronTask() {
    System.out.println("每分钟的整点执行一次");
}
```

### Cron 表达式格式
- 秒（0-59）
- 分（0-59）
- 时（0-23）
- 日期（1-31）
- 月份（1-12）
- 星期（0-7，0 和 7 都表示星期日）
  
#### 例子：
- `0 0 12 * * ?` ：每天中午12点执行。
- `0 0/5 14 * * ?` ：每天14:00到14:55之间，每5分钟执行一次。
- `0 0 12 1/2 * ?` ：每月的1号和15号中午12点执行。

通过 `@Scheduled` 注解，你可以非常灵活地配置定时任务规则。需要注意的是，默认情况下，Spring Boot 中的定时任务是单线程执行的。如果需要并发执行多个任务，可以自定义线程池来处理。

希望这些信息能帮助你更好地理解 Spring Boot 中的定时任务配置。



# spring-boot-demo-task

> 此 demo 主要演示了 Spring Boot 如何快速实现定时任务。

## pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <artifactId>spring-boot-demo-task</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>spring-boot-demo-task</name>
    <description>Demo project for Spring Boot</description>

    <parent>
        <groupId>com.xkcoding</groupId>
        <artifactId>spring-boot-demo</artifactId>
        <version>1.0.0-SNAPSHOT</version>
    </parent>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-lang3</artifactId>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

        <dependency>
            <groupId>cn.hutool</groupId>
            <artifactId>hutool-all</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <finalName>spring-boot-demo-task</finalName>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```

## TaskConfig.java

> 此处等同于在配置文件配置
>
> ```properties
> spring.task.scheduling.pool.size=20
> spring.task.scheduling.thread-name-prefix=Job-Thread-
> ```

```java
/**
 * <p>
 * 定时任务配置，配置线程池，使用不同线程执行任务，提升效率
 * </p>
 *
 * @package: com.xkcoding.task.config
 * @description: 定时任务配置，配置线程池，使用不同线程执行任务，提升效率
 * @author: yangkai.shen
 * @date: Created in 2018/11/22 19:02
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Configuration
@EnableScheduling
@ComponentScan(basePackages = {"com.xkcoding.task.job"})
public class TaskConfig implements SchedulingConfigurer {
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());
    }

    /**
     * 这里等同于配置文件配置
     * {@code spring.task.scheduling.pool.size=20} - Maximum allowed number of threads.
     * {@code spring.task.scheduling.thread-name-prefix=Job-Thread- } - Prefix to use for the names of newly created threads.
     * {@link org.springframework.boot.autoconfigure.task.TaskSchedulingProperties}
     */
    @Bean
    public Executor taskExecutor() {
        return new ScheduledThreadPoolExecutor(20, new BasicThreadFactory.Builder().namingPattern("Job-Thread-%d").build());
    }
}
```

## TaskJob.java

```java
/**
 * <p>
 * 定时任务
 * </p>
 *
 * @package: com.xkcoding.task.job
 * @description: 定时任务
 * @author: yangkai.shen
 * @date: Created in 2018/11/22 19:09
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Component
@Slf4j
public class TaskJob {

    /**
     * 按照标准时间来算，每隔 10s 执行一次
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void job1() {
        log.info("【job1】开始执行：{}", DateUtil.formatDateTime(new Date()));
    }

    /**
     * 从启动时间开始，间隔 2s 执行
     * 固定间隔时间
     */
    @Scheduled(fixedRate = 2000)
    public void job2() {
        log.info("【job2】开始执行：{}", DateUtil.formatDateTime(new Date()));
    }

    /**
     * 从启动时间开始，延迟 5s 后间隔 4s 执行
     * 固定等待时间
     */
    @Scheduled(fixedDelay = 4000, initialDelay = 5000)
    public void job3() {
        log.info("【job3】开始执行：{}", DateUtil.formatDateTime(new Date()));
    }
}
```





## 参考

- Spring Boot官方文档：https://docs.spring.io/spring-boot/docs/2.1.0.RELEASE/reference/htmlsingle/#boot-features-task-execution-scheduling
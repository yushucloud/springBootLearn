package com.bart.api.common.utils;

/**
 * 雪花算法
 * 生成 id
 * @Author noobBart
 * @Date 2023/7/19/0019 19:33
 */
public class SnowflakeUtils {

    // ============================== Basic field ==============================//

    // Datacenter id
    private long datacenterId;

    // Worker id
    private long workerId;

    // Increment sequence
    private long sequence;

    // ============================== Bits ==============================//

    // Bits of datacenter id
    private long datacenterIdBits;

    // Bits of worker id
    private long workerIdBits;

    // Bits of sequence
    private long sequenceBits;

    // ============================== Largest ==============================//

    // Largest datacenter id
    private long largestDatacenterId;

    // Largest worker id
    private long largestWorkerId;

    // Largest sequence
    private long largestSequence;

    // ============================== Shift ==============================//

    // Left shift num of worker id
    private long workerIdShift;

    // Left shift num of datacenter id
    private long datacenterIdShift;

    // Left shift num of timestamp
    private long timestampShift;

    // ============================== Other ==============================//

    // Epoch
    private long epoch;

    // The timestamp that last get snowflake id
    private long lastTimestamp;

    // ============================== End ==============================//

    public SnowflakeUtils(long dataCenterId, long workerId) {
        // Default epoch: 2022-07-22 00:00:00
        this(1658419200000L, -1L, dataCenterId, workerId, 5L, 5L, 5L);
    }

    public SnowflakeUtils(long epoch, long lastTimestamp, long datacenterId, long workerId,
                          long datacenterIdBits, long workerIdBits, long sequenceBits) {
        this.epoch = epoch;
        this.lastTimestamp = lastTimestamp;
        this.datacenterId = datacenterId;
        this.workerId = workerId;
        this.sequence = 0L;
        this.datacenterIdBits = datacenterIdBits;
        this.workerIdBits = workerIdBits;
        this.sequenceBits = sequenceBits;
        this.largestDatacenterId = ~(-1L << datacenterIdBits);
        this.largestWorkerId = ~(-1L << workerIdBits);
        this.largestSequence = ~(-1L << sequenceBits);
        if (datacenterId > largestDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(
                    String.format("The datacenter id param can't be greater than %s or less than 0",
                            largestDatacenterId));
        }
        if (workerId > largestWorkerId || workerId < 0) {
            throw new IllegalArgumentException(
                    String.format("The worker id param can't be greater than %s or less than 0",
                            largestWorkerId));
        }
        this.workerIdShift = sequenceBits;
        this.datacenterIdShift = workerIdShift + workerIdBits;
        this.timestampShift = datacenterIdShift + datacenterIdBits;
    }

    /**
     * Get snowflake id
     * @return
     */
    public synchronized long nextId() {
        long timestamp = System.currentTimeMillis();
        // 若时钟回退
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    "System clock moved backward, cannot to generate snowflake id");
        }
        // 若当前毫秒内多次生成雪花id
        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & largestSequence;
            // 序列溢出
            if (sequence == 0) {
                timestamp = waitUntilNextMilli(timestamp);
            }
        }
        // 若当前毫秒内首次生成雪花id
        else {
            sequence = 0L;
        }
        // 更新获取雪花id的时间戳
        lastTimestamp = timestamp;
        // 生成雪花id (通过位或运算符进行拼接)
        return ((timestamp - epoch) << timestampShift) // 时间戳段
                | (datacenterId << datacenterIdShift) // 机器码段
                | (workerId << workerIdShift) // 机器码段
                | sequence; // 自增序列段
    }

    /**
     * Wait until next millisecond
     * @param lastTimestamp
     * @return
     */
    private long waitUntilNextMilli(long lastTimestamp) {
        long currentTimeMillis;
        do {
            currentTimeMillis = System.currentTimeMillis();
        }
        while (currentTimeMillis <= lastTimestamp);
        return currentTimeMillis;
    }

    /**
     * Get util instance
     * @param dataCenterId
     * @param workerId
     * @return
     */
    public static SnowflakeUtils getInstance(long dataCenterId, long workerId) {
        return new SnowflakeUtils(dataCenterId, workerId);
    }
}
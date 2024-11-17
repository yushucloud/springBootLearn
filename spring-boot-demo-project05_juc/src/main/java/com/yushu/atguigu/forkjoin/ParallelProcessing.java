package com.yushu.atguigu.forkjoin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

public class ParallelProcessing extends RecursiveTask<Void> {
    private List<Integer> data;
    private static final int THRESHOLD = 10000; // 阈值

    public ParallelProcessing(List<Integer> data) {
        this.data = data;
    }

    @Override
    protected Void compute() {
        if (data.size() <= THRESHOLD) {
            // 处理小块数据
            data.forEach(d -> {
                // 数据处理逻辑
            });
        } else {
            int mid = data.size() / 2;
            ParallelProcessing leftTask = new ParallelProcessing(data.subList(0, mid));
            ParallelProcessing rightTask = new ParallelProcessing(data.subList(mid, data.size()));
            invokeAll(leftTask, rightTask);
        }
        return null;
    }

    public static void main(String[] args) {
        List<Integer> dataList = new ArrayList<>();
        for (int i = 0; i < 10000000; i++) {
            dataList.add(i);
        }

        ForkJoinPool pool = new ForkJoinPool();
        ParallelProcessing task = new ParallelProcessing(dataList);
        pool.invoke(task);
    }
}

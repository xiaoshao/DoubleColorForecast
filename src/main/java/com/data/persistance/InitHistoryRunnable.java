package com.data.persistance;

import com.prepare.data.ReadData;

import java.util.concurrent.CountDownLatch;

public class InitHistoryRunnable implements Runnable {
    private CountDownLatch countDownLatch;
    private ReadData readData;
    private int year;

    public InitHistoryRunnable(int year, CountDownLatch countDownLatch) {
        this.year = year;
        this.countDownLatch = countDownLatch;
        this.readData = new ReadData(RedisConnectionPool.getConnection(), String.valueOf(year));
    }

    @Override
    public void run() {
        this.readData.initData();
        this.countDownLatch.countDown();
        System.out.println("the year : " + year + " data is inited");
    }
}

package com.data.persistance;

import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;

public class HistoryDataInit {

    private int start = 2003;
    private int end;
    private CountDownLatch countDownLatch;

    public HistoryDataInit() {
        LocalDate localDate = LocalDate.now();
        this.end = localDate.getYear();

        this.countDownLatch = new CountDownLatch(end - start + 1);
    }

    public void init() throws InterruptedException {
        for (int index = start; index <= end; index++) {
            Thread readDataThread = new Thread(new InitHistoryRunnable(index, countDownLatch));
            readDataThread.start();
        }

        countDownLatch.await();
    }
}

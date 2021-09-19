package com.enjoyu.admin.common.concurrent;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorTest {
    @Test
    public void single() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<?> submit = executorService.submit(() -> {
            while (true) {
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName());
            }
        });
        Thread.sleep(1000);
        submit.cancel(true);
        Thread.sleep(3000);
        executorService.shutdownNow();
    }
    @Test
    public void singleThread() throws InterruptedException {
        Thread t = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("do not stop");
                }
                System.out.println(Thread.currentThread().getName());
            }
        });
        t.start();
        Thread.sleep(3000);
        t.interrupt();
        Thread.sleep(3000);
    }

}

package com.enjoyu.admin.common.concurrent;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {
    @Test
    public void test() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(5);
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "over");
                latch.countDown();
            });
            thread.setName("pal-" + i);
            thread.start();
        }
        latch.await();
        System.out.println("over");
    }
}

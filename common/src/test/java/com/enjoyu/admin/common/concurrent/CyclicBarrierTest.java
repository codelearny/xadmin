package com.enjoyu.admin.common.concurrent;

import org.junit.jupiter.api.Test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadLocalRandom;

public class CyclicBarrierTest {
    @Test
    public void test() throws InterruptedException, BrokenBarrierException {
        CyclicBarrier barrier = new CyclicBarrier(5);
        for (int i = 0; i < 15; i++) {
            Thread thread = new Thread(() -> {
                try {
                    int i1 = ThreadLocalRandom.current().nextInt(10000);
                    Thread.sleep(i1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    System.out.println(Thread.currentThread().getName() + "-" + barrier.getNumberWaiting() + "-waiting");
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
            thread.setName("bar-" + i);
            thread.start();
        }
        Thread.sleep(30000);
        barrier.reset();
        System.out.println("over");
    }
}

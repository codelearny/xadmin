package com.enjoyu.admin.common.concurrent;

import org.junit.jupiter.api.Test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Phaser;
import java.util.concurrent.ThreadLocalRandom;

public class PhaserTest {
    @Test
    public void test() throws InterruptedException, BrokenBarrierException {
        Phaser phaser = new Phaser();
        for (int i = 0; i < 15; i++) {
            Thread thread = new Thread(() -> {
                phaser.register();
                try {
                    int i1 = ThreadLocalRandom.current().nextInt(10000);
                    Thread.sleep(i1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "-" + phaser.getArrivedParties() + "-arrived");
                phaser.arriveAndAwaitAdvance();
            });
            thread.setName("bar-" + i);
            thread.start();
        }
        Thread.sleep(30000);
        System.out.println("over");
    }
}

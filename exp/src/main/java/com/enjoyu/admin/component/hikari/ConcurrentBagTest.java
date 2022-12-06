package com.enjoyu.admin.component.hikari;

import com.zaxxer.hikari.util.ConcurrentBag;

import java.util.concurrent.TimeUnit;

public class ConcurrentBagTest {


    /**
     * CopyOnWriteArrayList<T> sharedList 用来缓存所有的连接
     * ThreadLocal<List<Object>> threadList 用来缓存某个线程所使用的所有连接，相当于快速引用
     * AtomicInteger waiters 当前正在获取连接的等待者数量。AtomicInteger，就是一个自增对象。当 waiters 的数量大于 0 时候，意味着有线程正在获取资源。
     * SynchronousQueue<T> handoffQueue 0 容量的快速传递队列，非常有用。
     */
    public void a()  {
        ConcurrentBag<ConcurrentBag.IConcurrentBagEntry> cc = new ConcurrentBag<>(null);

        try {
            ConcurrentBag.IConcurrentBagEntry borrow = cc.borrow(1, TimeUnit.MINUTES);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

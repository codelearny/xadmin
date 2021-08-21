package com.enjoyu.admin.common.nio;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class DatagramChannelTest {
    private final InetSocketAddress address = new InetSocketAddress("127.0.0.1", 9999);

    @Test
    public void receive() throws IOException {
        DatagramChannel c = DatagramChannel.open();
        c.bind(address);
        ByteBuffer allocate = ByteBuffer.allocate(20);
        c.receive(allocate);
        System.out.println(new String(allocate.array()));
    }

    @Test
    public void send() throws IOException {
        DatagramChannel c = DatagramChannel.open();
        ByteBuffer allocate = ByteBuffer.allocate(48);
        allocate.put("有志者事竟成".getBytes());
        allocate.flip();
        c.send(allocate, address);
    }

    @Test
    public void conn() throws IOException {
        DatagramChannel c = DatagramChannel.open();
        c.connect(address);
        ByteBuffer allocate = ByteBuffer.allocate(96);
        allocate.put("破釜沉舟，百二秦关终属楚".getBytes());
        allocate.flip();
        c.write(allocate);
    }
}

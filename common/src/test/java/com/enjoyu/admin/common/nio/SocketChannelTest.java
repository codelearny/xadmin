package com.enjoyu.admin.common.nio;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class SocketChannelTest {

    private final InetSocketAddress address = new InetSocketAddress("127.0.0.1", 9999);

    @Test
    public void testBlockingServer() throws IOException {
        try (ServerSocketChannel ssc = ServerSocketChannel.open()) {
            ssc.socket().bind(address);
            try (SocketChannel socketChannel = ssc.accept()) {
                ByteBuffer readBuffer1 = ByteBuffer.allocate(200);
                ByteBuffer readBuffer2 = ByteBuffer.allocate(1000);
                socketChannel.read(new ByteBuffer[]{readBuffer1, readBuffer2});
                byte[] buf = new byte[1024];
                readBuffer1.flip();
                readBuffer1.get(buf,0,readBuffer1.remaining());
                System.out.println(new String(buf));
                byte[] buf2 = new byte[1024];
                readBuffer2.flip();
                readBuffer2.get(buf2, 0, readBuffer2.remaining());
                System.out.println(new String(buf2));
            }
        }
    }

    @Test
    public void testNoneBlockingServer() throws IOException {
        try (ServerSocketChannel ssc = ServerSocketChannel.open()) {
            ssc.socket().bind(address);
            ssc.configureBlocking(false);
            while (true) {
                try (SocketChannel socketChannel = ssc.accept()) {
                    if (socketChannel != null) {
                        // none-blocking
                        socketChannel.configureBlocking(false);
                        ByteBuffer readBuffer = ByteBuffer.allocate(100);
                        long read = 0;
                        while (read >= 0) {
                            read = socketChannel.read(readBuffer);
                            byte[] buf = new byte[1024];
                            readBuffer.flip();
                            readBuffer.get(buf, 0, readBuffer.remaining());
                            System.out.println(new String(buf));
                            readBuffer.clear();
                        }
                        System.out.println("over~");
                        break;
                    }
                }
            }
        }
    }

    @Test
    public void testClient() throws IOException {
        try (SocketChannel socketChannel = SocketChannel.open()) {
            socketChannel.connect(address);
            ByteBuffer writeBuffer1 = ByteBuffer.allocate(100);
            ByteBuffer writeBuffer2 = ByteBuffer.allocate(100);
            ByteBuffer writeBuffer3 = ByteBuffer.allocate(1000);
            writeBuffer1.put("虞美人".getBytes());
            writeBuffer2.put("春花秋月何时了".getBytes());
            writeBuffer3.put("春花秋月何时了，往事知多少？小楼昨夜又东风，故国不堪回首月明中！雕阑玉砌应犹在，只是朱颜改。问君能有几多愁？恰似一江春水向东流。".getBytes());
            writeBuffer1.flip();
            writeBuffer2.flip();
            writeBuffer3.flip();
            socketChannel.write(new ByteBuffer[]{writeBuffer1, writeBuffer2, writeBuffer3});
        }
    }
}

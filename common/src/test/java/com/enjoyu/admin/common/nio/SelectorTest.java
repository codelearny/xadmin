package com.enjoyu.admin.common.nio;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class SelectorTest {
    private final InetSocketAddress address = new InetSocketAddress("127.0.0.1", 9999);

    @Test
    public void selector() throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.socket().bind(address);
        ssc.configureBlocking(false);

        Selector selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> it = keys.iterator();

            while (it.hasNext()) {
                SelectionKey key = it.next();
                if (key.isAcceptable()) {
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = channel.accept();
                    socketChannel.configureBlocking(false);
                    SelectionKey key1 = socketChannel.register(selector, SelectionKey.OP_READ);
                    key1.attach(new Processor(socketChannel, key1));
                } else if (key.isReadable()) {
                    Processor p = (Processor) key.attachment();
                    p.onRead();
                } else if (key.isWritable()) {
                    Processor p = (Processor) key.attachment();
                    p.onWrite();
                }
            }
            keys.clear();
        }

    }

    @Test
    public void client() throws IOException, InterruptedException {
        SocketChannel socketChannel = SocketChannel.open();

        socketChannel.configureBlocking(false);
        socketChannel.socket().setSoLinger(false, -1);
        socketChannel.socket().setTcpNoDelay(true);

        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_CONNECT);
        socketChannel.connect(address);

        boolean running = true;
        int count = 0;
        while (running) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            for (SelectionKey k : selectionKeys) {
                SocketChannel sc = (SocketChannel) k.channel();
                if (k.isConnectable()) {
                    if (sc.finishConnect()) {
                        k.interestOps(SelectionKey.OP_WRITE);
                    }
                } else if (k.isWritable()) {
                    Thread.sleep(100);
                    ByteBuffer writeBuffer = ByteBuffer.allocate(20);
                    Random r = new Random();
                    int d = r.nextInt(20);
                    writeBuffer.putInt(d);
                    writeBuffer.flip();
                    sc.write(writeBuffer);
                    writeBuffer.clear();
                    System.out.println("write" + d);
                    if (++count > 1) {
                        k.interestOps(SelectionKey.OP_READ);
                    } else {
                        k.interestOps(SelectionKey.OP_WRITE);
                    }
                } else if (k.isReadable()) {
                    ByteBuffer readBuffer = ByteBuffer.allocate(20);
                    sc.read(readBuffer);
                    readBuffer.flip();
                    System.out.println(readBuffer.getInt());
                    k.cancel();
                    running = false;
                }
            }
            selectionKeys.clear();
        }

    }


}

class Processor {
    private final SocketChannel socketChannel;
    private final SelectionKey key;
    private final ByteBuffer buffer;
    private Callback callback;
    private int res;
    private int count;

    public Processor(SocketChannel socketChannel, SelectionKey key) {
        this.socketChannel = socketChannel;
        this.buffer = ByteBuffer.allocate(64);
        this.key = key;
        this.count = 0;
    }

    public void then(Callback callback) {
        this.callback = callback;
    }

    private void write(int res) throws IOException {
        buffer.clear();
        buffer.putInt(res);
        buffer.flip();
        socketChannel.write(buffer);
        socketChannel.close();
        key.cancel();

    }

    public void onRead() throws IOException {
        socketChannel.read(buffer);
        buffer.flip();
        int xi = buffer.getInt();
        System.out.println("received : " + xi);
        res = xi;
        if (++count > 1) {
            System.out.println("op_write");
            key.interestOps(SelectionKey.OP_WRITE);
        } else {
            System.out.println("op_read");
            key.interestOps(SelectionKey.OP_READ);
            then((x) -> {
                write(x + xi);
            });
        }
        buffer.clear();
    }

    public void onWrite() throws IOException {
        callback.call(res);
    }
}

interface Callback {
    void call(int x) throws IOException;
}

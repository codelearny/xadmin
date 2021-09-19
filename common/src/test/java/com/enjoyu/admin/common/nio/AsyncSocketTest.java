package com.enjoyu.admin.common.nio;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AsyncSocketTest {
    private final InetSocketAddress address = new InetSocketAddress("127.0.0.1", 9999);

    @Test
    public void server() throws IOException, InterruptedException {
        AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open();
        server.bind(address);
        server.accept(server, new CompletionHandler<AsynchronousSocketChannel, AsynchronousServerSocketChannel>() {
            @Override
            public void completed(AsynchronousSocketChannel client, AsynchronousServerSocketChannel server) {
                server.accept(server, this);
                ByteBuffer allocate = ByteBuffer.allocate(100);
                client.read(allocate, client, new CompletionHandler<Integer, AsynchronousSocketChannel>() {
                    @Override
                    public void completed(Integer result, AsynchronousSocketChannel cli) {
                        System.out.println(new String(allocate.array()));
                        allocate.flip();

                        byte[] bytes = new byte[allocate.limit()];
                        allocate.get(bytes);
                        System.out.println("receive:" + new String(bytes));

                        allocate.clear();
                        allocate.put("server --- back".getBytes());
                        allocate.flip();
                        cli.write(allocate);

                    }

                    @Override
                    public void failed(Throwable exc, AsynchronousSocketChannel cli) {
                        try {
                            cli.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println(exc);
                    }
                });

            }

            @Override
            public void failed(Throwable exc, AsynchronousServerSocketChannel server) {
                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(exc);
            }
        });
        Thread.currentThread().join();
    }

    @Test
    public void client() throws ExecutionException, InterruptedException, IOException {
        AsynchronousSocketChannel client = AsynchronousSocketChannel.open();
        Future<?> future = client.connect(address);
        //阻塞
        future.get();
        ByteBuffer allocate = ByteBuffer.allocate(100);
        allocate.put("hello".getBytes());
        client.write(allocate, allocate, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                attachment.clear();
                client.read(attachment, attachment, new CompletionHandler<Integer, ByteBuffer>() {
                    @Override
                    public void completed(Integer result, ByteBuffer back) {
                        try {
                            allocate.flip();

                            byte[] bytes = new byte[attachment.limit()];
                            attachment.get(bytes);
                            System.out.println(new String(bytes));

                            client.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failed(Throwable exc, ByteBuffer back) {
                        System.out.println(exc);
                    }
                });
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                System.out.println(exc);
            }
        });


    }
}

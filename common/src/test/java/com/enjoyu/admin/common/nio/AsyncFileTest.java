package com.enjoyu.admin.common.nio;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardOpenOption.READ;
import static java.nio.file.StandardOpenOption.WRITE;

public class AsyncFileTest {

    Path path = Paths.get("src/test/resources/async.txt");

    @Test
    public void read() throws IOException {
        Assert.assertTrue(Files.exists(path));
        AsynchronousFileChannel afc = AsynchronousFileChannel.open(path, READ);
        ByteBuffer allocate = ByteBuffer.allocate(1000);
        afc.read(allocate, 0, allocate, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                System.out.println("result = " + result);

                attachment.flip();
                byte[] data = new byte[attachment.limit()];
                attachment.get(data);
                System.out.println(new String(data));
                attachment.clear();
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                System.out.println(exc);
            }
        });
    }

    @Test
    public void write() throws IOException {
        Assert.assertTrue(Files.exists(path));
        AsynchronousFileChannel afc = AsynchronousFileChannel.open(path, WRITE);
        ByteBuffer allocate = ByteBuffer.allocate(1000);
        allocate.put("aw".getBytes());
        allocate.flip();
        afc.write(allocate, 0, "null", new CompletionHandler<Integer, String>() {
            @Override
            public void completed(Integer result, String attachment) {
                System.out.println("result = " + attachment);
                System.out.println("bytes written: " + result);
            }

            @Override
            public void failed(Throwable exc, String attachment) {
                System.out.println(exc);
            }
        });
    }

}

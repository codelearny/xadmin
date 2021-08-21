package com.enjoyu.admin.common.nio;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardOpenOption.READ;
import static java.nio.file.StandardOpenOption.WRITE;

public class FileChannelTest {
    Path path1 = Paths.get("src/test/resources/async.txt");
    Path path2 = Paths.get("src/test/resources/async2.txt");

    @Test
    public void transferFrom() throws IOException {
        FileChannel fromChannel = FileChannel.open(path1, READ);
        FileChannel toChannel = FileChannel.open(path2, WRITE);
        toChannel.transferFrom(fromChannel, 0, 1);
    }

    @Test
    public void transferTo() throws IOException {
        FileChannel fromChannel = FileChannel.open(path1, READ);
        FileChannel toChannel = FileChannel.open(path2, WRITE);
        fromChannel.transferTo(0, 1, toChannel);
    }

    @Test
    public void gather() throws IOException {
        FileChannel channel = FileChannel.open(path1, WRITE);
        channel.truncate(0);
        ByteBuffer head = ByteBuffer.allocate(50);
        ByteBuffer line1 = ByteBuffer.allocate(50);
        ByteBuffer line2 = ByteBuffer.allocate(50);
        ByteBuffer line3 = ByteBuffer.allocate(50);
        ByteBuffer line4 = ByteBuffer.allocate(50);
        head.put("赋得古原草送别\n".getBytes());
        line1.put("离离原上草\n".getBytes());
        line2.put("一岁一枯荣\n".getBytes());
        line3.put("野火烧不尽\n".getBytes());
        line4.put("春风吹又生\n".getBytes());
        head.flip();
        line1.flip();
        line2.flip();
        line3.flip();
        line4.flip();
        ByteBuffer[] bufferArray = {head, line1, line2, line3, line4};
        channel.write(bufferArray);
    }

    @Test
    public void scatter() throws IOException {
        FileChannel channel = FileChannel.open(path1, READ);
        ByteBuffer header = ByteBuffer.allocate(22);
        ByteBuffer body = ByteBuffer.allocate(200);
        ByteBuffer[] bufferArray = {header, body};
        channel.read(bufferArray);
        System.out.println(new String(header.array()));
        System.out.println(new String(body.array()));
    }
}

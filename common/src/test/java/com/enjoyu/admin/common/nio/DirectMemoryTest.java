package com.enjoyu.admin.common.nio;

import org.junit.jupiter.api.Test;
import sun.misc.SharedSecrets;
import sun.misc.VM;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardOpenOption.READ;
import static java.nio.file.StandardOpenOption.WRITE;

public class DirectMemoryTest {
    @Test
    public void ad() {
        ByteBuffer direct = ByteBuffer.allocateDirect(1024);
        System.out.println("运行时最大内存：" + Runtime.getRuntime().maxMemory());
        System.out.println("最大直接内存：" + VM.maxDirectMemory());
        System.out.println("NIO缓存池：" + SharedSecrets.getJavaNioAccess().getDirectBufferPool().getTotalCapacity());
    }

    Path path1 = Paths.get("src/test/resources/async.txt");
    Path path2 = Paths.get("src/test/resources/async2.txt");

    @Test
    public void mmap() throws IOException {
        FileChannel fromChannel = FileChannel.open(path1, READ);
        FileChannel toChannel = FileChannel.open(path2, READ, WRITE);
        MappedByteBuffer rbuf = fromChannel.map(FileChannel.MapMode.READ_ONLY, 0, fromChannel.size());
        MappedByteBuffer wbuf = toChannel.map(FileChannel.MapMode.READ_WRITE, 0, fromChannel.size());
        wbuf.put(rbuf);
    }

}

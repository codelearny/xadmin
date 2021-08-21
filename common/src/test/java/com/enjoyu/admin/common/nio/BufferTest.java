package com.enjoyu.admin.common.nio;

import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

public class BufferTest {

    @Test
    public void buffer() {
        ByteBuffer buffer2k = ByteBuffer.allocate(100);
        buffer2k.putInt(12);
        buffer2k.putInt(24);
        buffer2k.putInt(48);
        buffer2k.flip();
        int anInt = buffer2k.getInt(4);
        System.out.println(anInt);
    }

    @Test
    public void charBuffer() {
        ByteBuffer buffer2k = ByteBuffer.allocate(50);
        CharBuffer charBuffer = buffer2k.asCharBuffer();
        System.out.println("生成一个char类型的视图");
        charBuffer.put("hello");
        System.out.println("修改视图 put('hello')");
        System.out.println("视图 position " + charBuffer.position());
        System.out.println("原buffer position " + buffer2k.position());
        charBuffer.flip();
        System.out.println("flip");
        System.out.println("视图 position" + charBuffer.position());
        System.out.println("原buffer position " + buffer2k.position());
        char c = charBuffer.get(0);
        System.out.println("视图 get(0)" + c);
        System.out.println("原buffer -- " + new String(buffer2k.array()));
    }
}

package com.enjoyu.admin.common.nio;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

public class PipeTest {
    @Test
    public void pipe() throws IOException {
        Pipe pipe = Pipe.open();
        Pipe.SinkChannel sink = pipe.sink();
        ByteBuffer s = ByteBuffer.allocate(48);
        s.put("123456".getBytes());
        s.flip();
        sink.write(s);
        Pipe.SourceChannel source = pipe.source();
        ByteBuffer r = ByteBuffer.allocate(48);
        source.read(r);
        r.flip();
        while (r.hasRemaining()) {
            byte b = r.get();
            System.out.println((char) b);
        }
    }
}

package com.tank.util;

import lombok.SneakyThrows;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

public class PipeTest {

  @Test
  @SneakyThrows
  public void testPipe() {
    Pipe pipe = Pipe.open();
    ByteBuffer buffer = ByteBuffer.allocate(1024);
    buffer.put("hello".getBytes());
    buffer.flip();
    Pipe.SinkChannel sinkChannel = pipe.sink();
    sinkChannel.write(buffer);
    buffer.flip();
    System.out.println("xxx");

    buffer.rewind();

    Pipe.SourceChannel sourceChannel = pipe.source();
    int len = sourceChannel.read(buffer);
    System.out.println(new String(buffer.array(), 0, len));

    // 4. 关闭管道
    sinkChannel.close();
    sourceChannel.close();

  }

}

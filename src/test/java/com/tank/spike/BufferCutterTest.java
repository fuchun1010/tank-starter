package com.tank.spike;

import com.tank.message.ByteBufferCutter;
import com.tank.message.ChatBufferMessage;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.Optional;

public class BufferCutterTest {

  @Test
  public void emptyBuffer() {
    ByteBuffer buffer = ByteBuffer.allocate(1);
    Optional<byte[]> rs = ByteBufferCutter.cut(buffer, stopByte);
    Assert.assertTrue(!rs.isPresent());
  }

  @Test
  public void noDelimiter() {
    ByteBuffer buffer = ByteBuffer.allocate(32);
    byte[] data = {1, 2, 3};
    buffer.put(data);
    Optional<byte[]> rsOpt = ByteBufferCutter.cut(buffer, stopByte);
    Assert.assertTrue(!rsOpt.isPresent());
  }

  @Test
  public void single() {
    byte[] data = {1, 2, 3, 4, stopByte};
    ByteBuffer buffer = ByteBuffer.allocate(32);
    buffer.put(data);
    Optional<byte[]> rs = ByteBufferCutter.cut(buffer, stopByte);
    Assert.assertTrue(rs.isPresent());
  }

  @Test
  public void messageWithLeftOver() {
    byte[] bytes = {1, 2, 3, stopByte, 2, 3};
    byte[] truth = {1, 2, 3, stopByte};
    ByteBuffer buffer = ByteBuffer.allocate(16);
    buffer.put(bytes);
    Optional<byte[]> rsOpt = ByteBufferCutter.cut(buffer, stopByte);
    rsOpt.ifPresent(rs -> Assert.assertArrayEquals(rs, truth));
  }

  byte stopByte = ChatBufferMessage.MESSAGE_END;
}

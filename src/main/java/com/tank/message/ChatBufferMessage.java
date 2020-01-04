package com.tank.message;

import java.nio.ByteBuffer;

public class ChatBufferMessage implements MessageBuffer {

  public ChatBufferMessage() {
    this.buffer = ByteBuffer.allocate(MAX_CAPACITY);
  }

  @Override
  public void put(byte[] data) {
    int requireCapacity = this.buffer.position() + data.length;
    if (requireCapacity > MAX_CAPACITY) {
      requireCapacity = Math.max(requireCapacity, refactor * MAX_CAPACITY);
      this.buffer = ByteBuffer.allocate(requireCapacity);
    }
    this.buffer.put(data);

  }

  @Override
  public byte[] getNextMessage() {
    return ByteBufferCutter.cut(this.buffer, MESSAGE_END).orElseThrow(NullPointerException::new);
  }


  private final int refactor = 1 << 1;

  private final int MAX_CAPACITY = 1 << 7;

  private ByteBuffer buffer;

  public static byte MESSAGE_END = 0x1E;

}

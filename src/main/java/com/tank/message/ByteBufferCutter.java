package com.tank.message;

import lombok.NonNull;

import java.nio.ByteBuffer;
import java.util.Optional;

public class ByteBufferCutter {

  public static Optional<byte[]> cut(@NonNull final ByteBuffer buffer, byte stop) {
    int currentPosition = buffer.position();
    try {
      buffer.reset();
    } catch (Exception e) {
      buffer.rewind();
      buffer.mark();
    }

    boolean messageFound = false;

    while ((buffer.position() < currentPosition)) {
      if (buffer.get() == stop) {
        messageFound = true;
        break;
      }
    }

    if (!messageFound) {
      return Optional.empty();
    }

    int position = buffer.position();

    byte[] data = new byte[position];
    buffer.position(currentPosition);
    buffer.flip();
    buffer.get(data);
    buffer.compact();

    return Optional.ofNullable(data);
  }

}

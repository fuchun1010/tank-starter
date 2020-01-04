package com.tank.io;

import com.tank.message.ModeChangeRequest;
import com.tank.queue.ModeChangeRequestQueue;
import lombok.NonNull;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ConcurrentLinkedDeque;

public class NioSocketConnection extends Connection {

  public NioSocketConnection(@NonNull final SocketChannel socketChannel,
                             @NonNull final Selector selector,
                             @NonNull final ModeChangeRequestQueue queue) {
    this.socketChannel = socketChannel;
    this.selector = selector;
    this.queue = queue;
    this.writeBuffers = new ConcurrentLinkedDeque<>();
    this.byteBuffer = ByteBuffer.allocate(this.DEFAULT_CAPACITY);
    this.modeChangeRequestQueue = queue;
    this.mode = Mode.READ;
  }

  @Override
  protected void write(@NonNull byte[] data) {
    writeBuffers.add(ByteBuffer.wrap(data));
    if (this.mode == Mode.READ) {
      this.mode = Mode.WRITE;
      this.modeChangeRequestQueue.add(new ModeChangeRequest(this, SelectionKey.OP_READ));
      //TODO why?
      this.selector.wakeup();
    }
  }

  @Override
  protected byte[] fetchData() {
    return new byte[0];
  }


  public ModeChangeRequestQueue queue;

  public SocketChannel socketChannel;

  public Selector selector;

  private ByteBuffer byteBuffer;

  private final int DEFAULT_CAPACITY = 1 << 7;

  private ConcurrentLinkedDeque<ByteBuffer> writeBuffers;

  private enum Mode {READ, WRITE}

  private Mode mode;

  private ModeChangeRequestQueue modeChangeRequestQueue;
}

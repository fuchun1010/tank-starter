package com.tank.queue;

import com.tank.message.ModeChangeRequest;
import lombok.NonNull;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ModeChangeRequestQueue {

  public ModeChangeRequestQueue(@NonNull final Selector selector) {
    this.selector = selector;
    this.queue = new ConcurrentLinkedDeque<>();
  }

  public void add(@NonNull final ModeChangeRequest request) {
    this.queue.add(request);
  }

  public void process() {
    ModeChangeRequest request = queue.poll();
    while (request != null) {
      SelectionKey selectionKey = request.connection.socketChannel.keyFor(this.selector);
      if (Objects.nonNull(selectionKey) && selectionKey.isValid()) {
        selectionKey.interestOps(request.ops);
      }
      request = queue.poll();
    }
  }

  private ConcurrentLinkedDeque<ModeChangeRequest> queue;

  private Selector selector;
}

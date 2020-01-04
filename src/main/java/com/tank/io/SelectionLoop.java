package com.tank.io;

import com.tank.queue.ModeChangeRequestQueue;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;

@Slf4j
public class SelectionLoop implements Runnable {

  @SneakyThrows
  public SelectionLoop(@NonNull final ServerSocketChannel serverSocketChannel,
                       @NonNull final ArrayBlockingQueue<Connection> newConnections,
                       @NonNull final ArrayBlockingQueue<ConnectionEvent> connectionEvents) {

    serverSocketChannel.configureBlocking(false);
    this.selector = Selector.open();
    serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    this.newConnections = newConnections;
    this.connectionEvents = connectionEvents;
    this.modeChangeRequestQueue = new ModeChangeRequestQueue(this.selector);

  }

  @Override
  @SneakyThrows
  public void run() {
    while (true) {
      this.modeChangeRequestQueue.process();
      this.selector.select();
      Iterator<SelectionKey> iterator = this.selector.selectedKeys().iterator();
      while (iterator.hasNext()) {
        SelectionKey selectionKey = iterator.next();
        iterator.remove();
        if (selectionKey.isValid()) {
          continue;
        }
        if (selectionKey.isAcceptable()) {
          accept(selectionKey);
        } else if (selectionKey.isWritable()) {
          write(selectionKey);
        } else if (selectionKey.isReadable()) {
          read(selectionKey);
        }
      }
    }
  }


  private void read(SelectionKey selectionKey) {

  }

  private void write(SelectionKey selectionKey) {

  }

  @SneakyThrows
  private void accept(SelectionKey selectionKey) {
    ServerSocketChannel serverSocketChannel = ((ServerSocketChannel) selectionKey.channel());
    SocketChannel socketChannel = null;
    String clientAddress = "";
    try {
      socketChannel = serverSocketChannel.accept();
      clientAddress = socketChannel.getRemoteAddress().toString();
      log.info("[{}] connect server success", clientAddress);
    } catch (Exception e) {
      log.error("[{}] connect server failure", e.getMessage());
    }
    NioSocketConnection nioSocketConnection = new NioSocketConnection(socketChannel, selector, modeChangeRequestQueue);
    this.newConnections.put(nioSocketConnection);

  }


  protected ArrayBlockingQueue<Connection> newConnections;

  protected ArrayBlockingQueue<ConnectionEvent> connectionEvents;

  protected ModeChangeRequestQueue modeChangeRequestQueue;


  private Selector selector;

}

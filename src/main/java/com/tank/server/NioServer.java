package com.tank.server;

import com.tank.io.SelectionLoop;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;

@Slf4j
public class NioServer extends Server {

  public NioServer(@NonNull InetSocketAddress socketAddress) {
    super(socketAddress);
  }

  @Override
  @SneakyThrows
  public void start() {
    log.info("start server at:[{}]", this.socketAddress.getPort());
    ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
    serverSocketChannel.bind(super.socketAddress);
    SelectionLoop selectionLoop = new SelectionLoop(serverSocketChannel, newConnections, connectionEvents);
    Thread thread = new Thread(selectionLoop);
    thread.start();
  }
}

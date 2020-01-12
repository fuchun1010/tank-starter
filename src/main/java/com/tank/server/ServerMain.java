package com.tank.server;

import com.tank.io.Connection;
import com.tank.io.ConnectionEvent;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.concurrent.ArrayBlockingQueue;

@Slf4j
public class ServerMain {

  public static void main(String[] args) {
    ArrayBlockingQueue<Connection> newConnections = new ArrayBlockingQueue<>(DEFAULT_CAPACITY);
    ArrayBlockingQueue<ConnectionEvent> connectionEvents = new ArrayBlockingQueue<>(DEFAULT_CAPACITY);

    InetSocketAddress socketAddress = new InetSocketAddress(PORT);
    NioServer nioServer = new NioServer(socketAddress);

    nioServer.bind(newConnections, connectionEvents);
    nioServer.start();
  }


  private static final int DEFAULT_CAPACITY = 1 << 7;

  private static final int PORT = 10000;

}

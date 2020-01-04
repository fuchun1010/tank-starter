package com.tank.server;

import com.tank.io.Connection;
import com.tank.io.ConnectionEvent;
import lombok.NonNull;

import java.net.InetSocketAddress;
import java.util.concurrent.ArrayBlockingQueue;

public abstract class Server {

  public Server(@NonNull final InetSocketAddress socketAddress) {
    this.socketAddress = socketAddress;
  }

  public void bind(@NonNull final ArrayBlockingQueue<Connection> newConnections,
                   @NonNull final ArrayBlockingQueue<ConnectionEvent> connectionEvents) {
    this.newConnections = newConnections;
    this.connectionEvents = connectionEvents;
  }

  public abstract void start();

  protected InetSocketAddress socketAddress;

  protected ArrayBlockingQueue<Connection> newConnections;

  protected ArrayBlockingQueue<ConnectionEvent> connectionEvents;

}

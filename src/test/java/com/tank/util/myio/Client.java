package com.tank.util.myio;

import lombok.SneakyThrows;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import static java.nio.channels.SelectionKey.*;

public class Client {

  @Test
  @SneakyThrows
  public void initServerSocketChannel() {
    ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
    serverSocketChannel.bind(new InetSocketAddress(10000));
    serverSocketChannel.configureBlocking(false);
    Selector selector = Selector.open();
    serverSocketChannel.register(selector, OP_CONNECT | OP_WRITE | OP_READ | OP_ACCEPT);

    while (selector.select() > 0) {
      Iterator<SelectionKey> selectedKeys = selector.selectedKeys().iterator();
      while (selectedKeys.hasNext()) {
        SelectionKey selectedKey = selectedKeys.next();

        if (selectedKey.isAcceptable()) {
          SocketChannel socketChannel = serverSocketChannel.accept();
          socketChannel.configureBlocking(false);
          socketChannel.register(selector, OP_READ);
        } else if (selectedKey.isReadable()) {
          SocketChannel readChannel = ((SocketChannel) selectedKey.channel());
          ByteBuffer buffer = ByteBuffer.allocate(1024);
          while (readChannel.read(buffer) != -1) {
            buffer.flip();
            System.out.println(new String(buffer.array()));
            buffer.clear();
          }
        }

        selectedKeys.remove();
      }
    }
  }
}

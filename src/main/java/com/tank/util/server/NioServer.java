package com.tank.util.server;

import lombok.NonNull;
import lombok.SneakyThrows;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static java.nio.channels.SelectionKey.*;

public class NioServer {

  public static void main(String[] args) {

    NioServer nioServer = new NioServer(10000);
    nioServer.startNioServer();
  }

  public NioServer(@NonNull Integer port) {
    this.port = port;
  }

  @SneakyThrows
  public void startNioServer() {
    InetSocketAddress address = new InetSocketAddress(this.port);
    serverSocketChannel = ServerSocketChannel.open();
    serverSocketChannel.configureBlocking(false);
    serverSocketChannel.bind(address);
    System.out.println(String.format("listen port:[%d] success", this.port));
    Selector selector = Selector.open();
    //| OP_READ | OP_CONNECT | OP_WRITE
    serverSocketChannel.register(selector, OP_ACCEPT);
    this.loopSelector(selector);
  }

  @SneakyThrows
  private void loopSelector(Selector selector) {

    while (true) {
      int rs = selector.select();
      if (rs < 0) {
        Thread.sleep(50);
        continue;
      }
      Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
      while (iterator.hasNext()) {

        SelectionKey selectionKey = iterator.next();

        synchronized (selectionKey) {
          iterator.remove();
        }

        if (!selectionKey.isValid()) {
          continue;
        }

        if (selectionKey.isAcceptable()) {
          this.handle(selectionKey, key -> {
            logger.info("accept connection");
            try {
              SocketChannel socketChannel = this.serverSocketChannel.accept();
              socketChannel.configureBlocking(false);
              socketChannel.register(key.selector(), OP_READ);
            } catch (Exception e) {
              System.err.println(String.format("accept exception:[%s]", e.getMessage()));
            }
          });
        } else if (selectionKey.isReadable()) {
          this.handle(selectionKey, key -> {
            SocketChannel channel = ((SocketChannel) key.channel());
            ByteBuffer buffer = ByteBuffer.allocate(8);
            byte[] target = null;
            try {
              while (true) {
                int n = channel.read(buffer);
                if (n <= 0) {
                  break;
                }
                int len = buffer.position();
                buffer.flip();
                byte[] tmpData = new byte[len];
                buffer.get(tmpData);
                target = Objects.isNull(target) ? tmpData : this.concat(target, tmpData);
                buffer.compact();
                buffer.clear();
              }

              String data = new String(target);
              String words = data.replace("\r\n", "");
              if ("quit".equalsIgnoreCase(words)) {
                channel.close();
              }
              System.out.println(String.format("receive data:[%s]", words));
              channel.configureBlocking(false);
              channel.register(key.selector(), OP_WRITE, words);
            } catch (Exception e) {
              e.printStackTrace();
            }
          });
        } else if (selectionKey.isWritable()) {

          this.handle(selectionKey, key -> {
            SocketChannel channel = ((SocketChannel) key.channel());
            try {
              String words = ((String) key.attachment());
              ByteBuffer data = ByteBuffer.wrap(words.getBytes());
              channel.write(data);
              channel.configureBlocking(false);
              channel.register(key.selector(), OP_READ, words);
            } catch (Exception e) {
              System.err.println(String.format("write exception:[%s]", e.getMessage()));
            }

          });
        }


      }
    }
  }

  private byte[] concat(byte[]... bytes) {
    int capacity = Stream.of(bytes).map(b -> Objects.isNull(b) ? 0 : b.length).reduce(0, Integer::sum);
    byte[] targets = new byte[capacity];

    int startIndex = 0;
    for (byte[] bt : bytes) {
      System.arraycopy(bt, 0, targets, startIndex, bt.length);
      startIndex += bt.length;
    }

    return targets;
  }

  public void handle(@NonNull final SelectionKey selectionKey,
                     @NonNull final Consumer<SelectionKey> handler) {
    handler.accept(selectionKey);
  }


  private String ip;
  private Integer port;

  private ServerSocketChannel serverSocketChannel;

  Logger logger = Logger.getLogger(NioServer.class.getName());
}

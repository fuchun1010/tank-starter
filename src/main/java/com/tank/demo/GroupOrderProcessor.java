package com.tank.demo;

import lombok.NonNull;
import lombok.SneakyThrows;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;

public class GroupOrderProcessor<T> implements Runnable {

  public GroupOrderProcessor(@NonNull final ConcurrentLinkedDeque<T> deque, boolean running) {
    this.deque = deque;
    this.running = running;
  }

  public int remaining() {
    return this.deque.size();
  }

  @Override
  @SneakyThrows
  public void run() {
    System.out.println(format("thread:[%s] start success", Thread.currentThread().getName()));
    while (running) {
      Thread.sleep(TimeUnit.MICROSECONDS.toMillis(50));
      T data = this.deque.poll();
      if (data == null) {
        Thread.sleep(50);
        continue;
      }
      String tips = format("thread name is:[%s], data is:[%s], size is:[%d]",
          Thread.currentThread().getName(), data.toString(), this.deque.size());
      System.out.println(tips);
    }

  }

  public void stop() {
    this.running = false;
  }


  private ConcurrentLinkedDeque<T> deque;

  private volatile boolean running = false;
}

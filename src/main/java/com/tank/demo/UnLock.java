package com.tank.demo;

import java.util.stream.IntStream;

import static java.lang.String.format;

public class UnLock {




  public static void main(String[] args) {
    IntStream.rangeClosed(1, 5).boxed().forEach(index -> new Thread(UnLock::add, format("thread-%d", index)).start());
  }

  private static void add() {
    for (int i = 0; i < 5; i++) {
      Integer rs = threadLocal.get() == null ? 0 : threadLocal.get();
      rs = rs + 1;
      threadLocal.set(rs);
      System.out.println(format("thread name is: %s, rs is: %d", Thread.currentThread().getName(), rs));

    }
    threadLocal.remove();
  }

  private static ThreadLocal<Integer> threadLocal = new ThreadLocal<>();
}

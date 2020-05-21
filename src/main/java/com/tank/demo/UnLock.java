package com.tank.demo;

import java.util.stream.IntStream;

import static java.lang.String.format;

public class UnLock {

  public static void main(String[] args) {
    IntStream.rangeClosed(1, 5).boxed().forEach(index -> new Thread(UnLock::add, format("thread-%d", index)).start());
  }

  private static void add() {
    ThreadLocal<Integer> threadLocal = new ThreadLocal<>();
    for (int i = 1; i < 100; i++) {
      Integer rs = threadLocal.get() == null ? 0 : threadLocal.get();
      rs = rs + i;
      threadLocal.set(rs);
    }
    System.out.println(format("thread name is: %s, rs is: %d", Thread.currentThread().getName(), threadLocal.get()));
    threadLocal.remove();
  }

  //private static ThreadLocal<Integer> threadLocal = new ThreadLocal<>();
}

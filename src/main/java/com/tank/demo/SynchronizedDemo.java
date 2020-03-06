package com.tank.demo;

import sun.misc.Unsafe;

public class SynchronizedDemo implements Runnable {

  private static int count = 0;
  private ThreadLocal<Integer> local = ThreadLocal.withInitial(() -> 0);

  public static void main(String[] args) {
    for (int i = 0; i < 10; i++) {
      Thread thread = new Thread(new SynchronizedDemo());
      thread.start();
    }
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("result: " + count);
  }

  @Override
  public void run() {
    int initValue = local.get();
    for (int i = 0; i < 1000000; i++) {
      initValue++;
      local.set(initValue);
    }
    count += local.get();
    local.remove();

  }


}

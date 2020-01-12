package com.tank.splitter;

import java.util.concurrent.atomic.AtomicLong;

public class OrderGenerator {

  public long generateOrderNo() {
    return atomicInteger.incrementAndGet();
  }

  private AtomicLong atomicInteger = new AtomicLong(100L);
}

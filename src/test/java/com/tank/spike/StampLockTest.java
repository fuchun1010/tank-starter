package com.tank.spike;

import lombok.SneakyThrows;
import org.junit.Test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static java.lang.String.format;

public class StampLockTest {

  @Test
  @SneakyThrows
  public void testAdd() {
    Counter counter = new Counter(100);
    counter.addWithMultiThread();
    System.out.println(counter.counterValue());
  }


  private static class Counter implements Runnable {

    public Counter(int threadNumber) {
      this.threadNumber = threadNumber;
      this.threadCounter = threadNumber;
    }

    public void addWithMultiThread() {
      for (int i = 0; i < this.threadNumber; i++) {
        new Thread(this, format("thread-%d", i)).start();
      }
    }

    public int counterValue() {
      try {
        this.writeLock.lock();
        this.displayCondition.await();
      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally {
        this.writeLock.unlock();
      }
      return this.counter;
    }

    @Override
    public void run() {
      try {
        writeLock.lock();
        ++this.counter;
        --this.threadCounter;
        if (this.threadCounter == 0) {
          this.displayCondition.signal();
        }
      } finally {
        writeLock.unlock();
      }
    }

    private int counter = 0;

    private int threadNumber;

    private int threadCounter;

    private ReentrantReadWriteLock reentrantLock = new ReentrantReadWriteLock();

    private Lock writeLock = reentrantLock.writeLock();

    private Condition displayCondition = writeLock.newCondition();
  }


}

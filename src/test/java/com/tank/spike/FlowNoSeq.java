package com.tank.spike;

import com.annimon.stream.IntStream;
import lombok.SneakyThrows;
import org.junit.Test;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.String.format;

public class FlowNoSeq {


  @Test
  @SneakyThrows
  public void testWork() {


    new Thread(() -> {

      while (true) {
        try {
          reentrantLock.lock();
          if (queue.size() == 0) {
            System.out.println(format("==========thread-%s 小于%d,等着生产==========",
                Thread.currentThread().getName(), max));
            producerCondition.signal();
            try {
              consumerCondition.await();
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
          System.out.println(format("*******thread-%s,取出:%d*************",
              Thread.currentThread().getName(), queue.poll()));
        } finally {
          reentrantLock.unlock();
        }
      }

    }, "consumer").start();

    new Thread(() -> {
      try {
        reentrantLock.lock();
        while (true) {
          if (queue.size() == 0) {
            IntStream.rangeClosed(1, max).boxed().forEach(index -> queue.add(index));
            System.out.println(format("!!!!thread-%s 生产%d个!!!!", Thread.currentThread().getName(), max));
            consumerCondition.signal();
          }
          try {
            producerCondition.await();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      } finally {
        reentrantLock.unlock();
      }

    }, "producer").start();

    while (true) {
      TimeUnit.MILLISECONDS.sleep(max);
    }

  }

  @Test
  public void testQueue() {
    IntStream.rangeClosed(1, max).boxed().forEach(index -> queue.add(index));
    while (!queue.isEmpty()) {
      System.out.println(queue.poll());
    }
    System.out.println(queue.size());
  }


  private ConcurrentLinkedDeque<Integer> queue = new ConcurrentLinkedDeque<>();

  private ReentrantLock reentrantLock = new ReentrantLock();
  private Condition consumerCondition = reentrantLock.newCondition();
  private Condition producerCondition = reentrantLock.newCondition();
  private int max = 5;

}

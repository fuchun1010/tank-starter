package com.tank.order;

import com.google.common.collect.Queues;
import lombok.SneakyThrows;

import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author tank198435163.com
 */
public class PkSequence {


  @SneakyThrows
  public Long primaryKey() {
    try {
      lock.lock();
      if (seq.size() <= minLimitation) {
        produceCondition.signalAll();
        consumerCondition.await();
      }
      return seq.poll();
    } finally {
      lock.unlock();
    }
  }

  public void generatePrimaryKey() {
    try {
      lock.lock();

      int differ = maxLimitation - seq.size();

      if (differ > 0) {
        System.out.println("需要补充" + differ + "个");
      }

      for (int i = 0; i < differ; i++) {
        seq.add((long) i);
      }

    } finally {
      lock.unlock();
    }
  }

  public void monitorPrimaryKeySequence() {

    Thread thread = new Thread(() -> {
      System.out.println(Thread.currentThread().getName() + " 已经启动");
      for (; ; ) {
        try {
          lock.lock();
          produceCondition.await();
          this.generatePrimaryKey();
          consumerCondition.signalAll();
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          lock.unlock();
        }
      }

    }, "订单主键队列监控线程");
    thread.start();

  }

  private Queue<Long> seq = Queues.newConcurrentLinkedQueue();

  private ReentrantLock lock = new ReentrantLock();

  private Condition consumerCondition = lock.newCondition();

  private Condition produceCondition = lock.newCondition();

  private int minLimitation = 500;

  private int maxLimitation = 20000;


}

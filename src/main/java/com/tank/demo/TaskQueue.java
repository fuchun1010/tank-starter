package com.tank.demo;

import com.google.common.collect.Queues;
import lombok.NonNull;

import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TaskQueue<T> {

  public TaskQueue() {
    this.taskQueue = Queues.<T>newArrayDeque();
  }

  public void addTask(@NonNull final T task) {
    try {
      reentrantLock.lock();
      if (this.taskQueue.size() >= 10) {
        System.out.println("await");
        cc.await();
      }
      this.taskQueue.add(task);
      pc.signal();

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      reentrantLock.unlock();
    }
  }

  public void processTask() {
    try {
      reentrantLock.lock();
      try {
        System.out.println("process start");
        for (; ; ) {
          if (taskQueue.size() == 0) {
            pc.await();
          }
          //TimeUnit.MILLISECONDS.sleep(1000);
          T result = taskQueue.poll();
          System.out.println(result + " size:" + taskQueue.size());
          if (taskQueue.size() < 10) {
            cc.signal();
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    } finally {
      reentrantLock.unlock();
    }
  }

  public void print() {
    while (!this.taskQueue.isEmpty()) {
      T task = this.taskQueue.poll();
      System.out.println(task);
    }
  }

  private Queue<T> taskQueue;

  private final ReentrantLock reentrantLock = new ReentrantLock();

  private final Condition pc = reentrantLock.newCondition();
  private final Condition cc = reentrantLock.newCondition();
}

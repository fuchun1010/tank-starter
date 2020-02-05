package com.tank.wheel;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

@Slf4j
public class SecondWheel implements Runnable {

  public SecondWheel() {
    this(60);
    this.wheel = new Set[this.size];
    this.queue = new ArrayBlockingQueue<>(this.size);

  }

  public SecondWheel(int size) {
    if (size < 0) {
      throw new IllegalArgumentException("size < 0");
    }
    this.size = size;
    this.wheel = new Set[this.size];
    this.queue = new ArrayBlockingQueue<>(this.size);

  }

  public void addCustomerJob(final CustomerJob customerJob) {

    int seconds = customerJob.getSchedual().getSeconds();
    int remaining = seconds % this.size + pointer;
    int index = remaining % this.size;
    int round = seconds / this.size;

    customerJob.setRound(round);
    customerJob.setIndex(index);

    synchronized (this.wheel) {
      boolean isEmpty = this.wheel[index] == null;
      if (isEmpty) {
        this.wheel[index] = new HashSet<>();
      }
      boolean isFull = this.wheel[index].size() == maxItemNum;
      if (isFull) {
        log.info("请往数据库丢");
        return;
      }
      this.wheel[index].add(customerJob);
    }

  }

  @Override
  @SneakyThrows
  public void run() {
    while (true) {
      if (pointer == this.size) {
        pointer = 0;
      }

      if (this.wheel[pointer] != null) {
        this.queue.add(this.wheel[pointer]);
        System.out.println();
      }
      Thread.sleep(1000);
      pointer++;

    }
  }

  private void handleIndex(Set<CustomerJob> customerJobs) {
    boolean isOk = customerJobs != null;
    if (isOk) {
      //TODO 这个地方应该异步处理
      synchronized (wheel) {
        Iterator<CustomerJob> iterator = customerJobs.iterator();
        while (iterator.hasNext()) {
          CustomerJob customerJob = iterator.next();
          customerJob.decrement();
          log.info("做减法了->" + customerJob.getId());
          if (customerJob.isReady()) {
            iterator.remove();
            //TODO 这个地方也改异步
            log.info("处理该job" + customerJob.getId());

          }

        }
      }
    }

  }

  @SneakyThrows
  public void checkRound() {
    new Thread(() -> {
      System.out.println(queue.size());
      while (true) {
        try {
          Set<CustomerJob> customerJob = queue.take();
          if (customerJob != null) {
            this.handleIndex(customerJob);
          }
        } catch (Exception e) {
          System.out.println(e.toString());
        }
      }

    }).start();
  }

  private int size;

  private int maxItemNum = 512;

  private int pointer = 0;

  private Set<CustomerJob>[] wheel = null;

  private ArrayBlockingQueue<Set<CustomerJob>> queue;


}

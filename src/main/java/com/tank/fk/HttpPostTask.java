package com.tank.fk;

import lombok.NonNull;

import java.util.Queue;
import java.util.concurrent.RecursiveTask;

public class HttpPostTask extends RecursiveTask<Integer> {

  public HttpPostTask(@NonNull final Queue<Integer> queue) {
    this.queue = queue;
  }


  @Override
  protected Integer compute() {
    int sum = 0;
    boolean isOk = !queue.isEmpty() && queue.size() >= threshold;
    if (isOk) {
      for (int i = 0; i < threshold; i++) {
        sum += queue.poll();
      }
    }

    if (!queue.isEmpty()) {
      HttpPostTask task = new HttpPostTask(queue);
      task.fork();
      sum += task.join();
    }

    return sum;
  }

  private Queue<Integer> queue;

  private int threshold = 100;
}

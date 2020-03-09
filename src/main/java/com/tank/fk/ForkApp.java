package com.tank.fk;

import com.annimon.stream.IntStream;
import com.google.common.collect.Queues;
import lombok.SneakyThrows;

import java.util.Queue;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

import static java.lang.String.format;

public class ForkApp {
  @SneakyThrows
  public static void main(String[] args) {
    ForkJoinPool forkJoinPool = new ForkJoinPool();
    Queue<Integer> queue = Queues.newArrayDeque();
    IntStream.rangeClosed(1, 4).boxed().forEach(queue::add);
    HttpPostTask task = new HttpPostTask(queue);
    Future<Integer> result = forkJoinPool.submit(task);
    System.out.println(format("result = %d", result.get()));
    forkJoinPool.shutdown();

  }
}

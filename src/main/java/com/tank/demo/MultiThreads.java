package com.tank.demo;

import com.google.common.collect.Sets;
import lombok.Cleanup;
import lombok.SneakyThrows;

import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.TimeUnit;

public class MultiThreads {

  @SneakyThrows
  public static void main(String[] args) {

    int num = 6;

    GroupOrderProcessor<String> processor;
    ConcurrentLinkedDeque<String> queue = new ConcurrentLinkedDeque<>();
    Thread thread;
    Set<GroupOrderProcessor<String>> processors = Sets.newHashSet();
    
    for (int i = 0; i < num; i++) {
      processor = new GroupOrderProcessor<>(queue, flag);
      thread = new Thread(processor);
      thread.setName(String.format("groupOrderProcessor-%d", i));
      thread.start();
      processors.add(processor);
    }

    Thread.sleep(TimeUnit.SECONDS.toMillis(1));

    System.out.println("input command:");
    @Cleanup Scanner scanner = new Scanner(System.in);
    while (flag) {
      String command = scanner.next();
      if ("quit".equalsIgnoreCase(command)) {
        flag = false;
        processors.forEach(GroupOrderProcessor::stop);
        break;
      }
      queue.add(command);
    }

    System.out.println("end");

  }

  private static volatile boolean flag = true;
}

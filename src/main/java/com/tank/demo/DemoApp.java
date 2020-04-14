package com.tank.demo;

import com.annimon.stream.IntStream;

public class DemoApp {
  public static void main(String[] args) {
    TaskQueue<String> queue = new TaskQueue<>();
    new Thread(() -> IntStream.range(0, 800).boxed()
        .map(String::valueOf).forEach(queue::addTask), "addTask")
        .start();
    new Thread(queue::processTask, "process").start();
  }
}

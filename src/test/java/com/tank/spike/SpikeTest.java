package com.tank.spike;

import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import org.junit.Test;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class SpikeTest {

  @Test
  public void testEnd() {
    List<String> fields = Lists.newLinkedList();
    fields.add("user text");
    String delimiter = Character.toString((char) MESSAGE_DELIMITER);
    String end = Character.toString((char) MESSAGE_END);
    String rs = String.format("%s%s", String.join(delimiter, fields), end);
    System.out.println(rs);
  }


  @Test
  public void testDeque() {
    ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
    deque.add("hello");
    String item = deque.poll();
    while (Objects.nonNull(item)) {
      System.out.println(item);
      item = deque.poll();

    }
  }

  @Test
  @SneakyThrows
  public void testBlockQueue() {
    ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(capacity);
    queue.put("hello");
    System.out.println(queue.size());
  }

  @Test
  public void testIndex() {
    System.out.println(index);
  }


  private byte MESSAGE_DELIMITER = 0x1F;

  private byte MESSAGE_END = 0x1E;

  private int index = 2 >> 1;

  private int capacity = 1 << 7;
}

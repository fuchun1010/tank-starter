package com.tank.spike;

import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import lombok.val;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedDeque;

import static java.lang.String.format;

public class SpikeTest {

  @Test
  public void testDiff() {
    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    val start = LocalDateTime.now();
    val end = df.parse("2020-01-31 23:59:59");
    long differWithMinutes = Duration.between(start, LocalDateTime.from(end)).toMinutes();

    long round = differWithMinutes / 60;

    long offset = differWithMinutes % 60;

    System.out.println(format("round = %d, slot = %d", round, offset));

  }


  @Test
  public void testEnd() {
    List<String> fields = Lists.newLinkedList();
    fields.add("user text");
    String delimiter = Character.toString((char) MESSAGE_DELIMITER);
    String end = Character.toString((char) MESSAGE_END);
    String rs = format("%s%s", String.join(delimiter, fields), end);
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

package com.tank.spike;

import com.annimon.stream.IntStream;
import com.annimon.stream.Stream;
import com.google.common.collect.Lists;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.String.format;

@Slf4j
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
  public void testTimeUnitConvert() {
//    long result2 = TimeUnit.NANOSECONDS.convert(1, TimeUnit.MILLISECONDS);
//    long result = TimeUnit.SECONDS.convert(1, TimeUnit.MINUTES);
//    System.out.println(result2);

    CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> {
      try {
        TimeUnit.SECONDS.sleep(1);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      return 1;
    }).thenApply(index -> index + 1).exceptionally(ex -> {
      System.out.println(ex.getMessage());
      return -1;
    });


    CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(() -> {
      try {
        TimeUnit.SECONDS.sleep(100);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      return 1;
    }).thenApply(index -> index + 1);

    val start = LocalDateTime.now();
    CompletableFuture.allOf(f1, f2).join();
    System.out.println("over");
  }

  @Test
  public void testThreadPool() {
    ThreadPoolExecutor executor = new ThreadPoolExecutor(1,
        Runtime.getRuntime().availableProcessors(),
        50, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(200),
        new CustomerThreadFactory("calculate thread"),
        new ThreadPoolExecutor.CallerRunsPolicy());

    CompletableFuture<Integer> future = CompletableFuture
        .supplyAsync(() -> IntStream.rangeClosed(1, 100).boxed().toList(), executor)
        .thenApplyAsync(list -> Stream.of(list).reduce(0, Integer::sum))
        .exceptionally(ex -> {
          System.err.println(ex.getMessage());
          return -1;
        });

    Integer rs = future.join();
    System.out.println(rs);
  }

  private static class CustomerThreadFactory implements ThreadFactory {

    public CustomerThreadFactory(@NonNull final String threadName) {
      this.threadName = threadName;
    }

    @Override
    public Thread newThread(Runnable r) {
      String entireName = format("%s-->%d", this.threadName, counter.getAndIncrement());
      Thread thread = new Thread(r, entireName);
      log.info(thread.getName());
      return thread;
    }

    private String threadName;

    private AtomicInteger counter = new AtomicInteger(1);
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

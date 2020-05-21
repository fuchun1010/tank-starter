package com.tank.fun;

import io.vavr.Tuple2;
import io.vavr.collection.Queue;
import io.vavr.collection.Stream;
import io.vavr.control.Try;
import lombok.val;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static io.vavr.API.*;
import static io.vavr.Patterns.$Failure;
import static io.vavr.Patterns.$Success;
import static io.vavr.Predicates.instanceOf;

public class NewFunctionTest {

  @Test
  public void testOption() {
    final List<Try<Integer>> results = Stream.of(6, 4, 1, 2)
            .map(data -> Try.of(() -> data / (data - 1)))
            .collect(Collectors.toList());

    for (Try<Integer> tryResult : results) {
      try {
        int result = tryResult.getOrElseThrow(() -> new Exception("除法异常"));
        System.out.println(result);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  @Test
  public void testStream() {
    Stream.of(1, 2, 3).append(4).cycle(2).forEach(System.out::println);
  }

  @Test
  public void testExp() {
    Integer result = Try.of(() -> 6 / 2)
            .recover(x -> Match(x).of(
                    Case($(instanceOf(Exception.class)), t -> {
                      System.out.println(t.getMessage());
                      return -1;
                    })
            ))
            .getOrElse(-1);
    System.out.println(result);
  }

  @Test
  public void testExp2() {
    val result = Match(Try.of(() -> 6 / 2)).of(
            Case($Success($()), d -> d),
            Case($Failure($()), () -> new Exception(""))
    );
    System.out.println(result.getClass().getSimpleName());
  }

  @Test
  public void testMaxLong() {
    System.out.println(Long.MAX_VALUE);
  }

  @Test
  public void testList() {
    io.vavr.collection.List<Integer> lists = List(1, 2, 3, 4, 5);
    lists.tail().prepend(0).forEach(System.out::println);
    val result = lists.headOption().flatMap(d -> {
      int v = d - 1;
      return Option(Try.of(() -> d / v));
    });
    val value = Match(result.get()).of(
            Case($Success($()), d -> d),
            Case($(instanceOf(Exception.class)), ex -> {
              return -1;
            })
    );
    System.out.println(value);
  }

  @Test
  public void testQueue() {
    Queue<Integer> queue = Queue(1, 2, 3, 4);
    Tuple2<Integer, Queue<Integer>> value = queue.dequeue();
    value._2.forEach(System.out::println);
  }

}

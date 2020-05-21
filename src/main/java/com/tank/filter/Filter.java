package com.tank.filter;

import lombok.extern.slf4j.Slf4j;

import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author tank198435163.com
 */
@Slf4j
public class Filter {

  public static <T> Stream<T> takeWhile(Stream<T> stream, Predicate<T> predicate) {
    CustomSpliterator<T> customSpliterator = new CustomSpliterator<>(stream.spliterator(), predicate);
    return StreamSupport.stream(customSpliterator, false);
  }

  public static void main(String[] args) {
    Stream<Integer> ints = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    CustomSpliterator<Integer> spliterator = new CustomSpliterator<>(ints.spliterator(), x -> x > 5);
    StreamSupport.stream(spliterator, false)
            .collect(Collectors.toList())
            .forEach(System.out::println);
  }


}



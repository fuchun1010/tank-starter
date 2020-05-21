package com.tank.filter;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @param <T>
 * @author tank198435163.com
 */
public class CustomSpliterator<T> extends Spliterators.AbstractSpliterator<T> {


  public CustomSpliterator(Spliterator<T> splitr, Predicate<T> predicate) {
    super(splitr.estimateSize(), 0);
    this.splitr = splitr;
    this.predicate = predicate;
  }

  @Override
  public synchronized boolean tryAdvance(Consumer<? super T> consumer) {
    boolean hadNext = splitr.tryAdvance(elem -> {
      this.isMatched = true;
      if (predicate.test(elem) && isMatched) {
        consumer.accept(elem);
      } else {
        isMatched = false;
      }
    });
    return hadNext;
  }

  private Spliterator<T> splitr;
  private Predicate<T> predicate;
  private volatile boolean isMatched = true;
}

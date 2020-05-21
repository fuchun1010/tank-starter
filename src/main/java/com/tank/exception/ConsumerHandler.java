package com.tank.exception;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.function.Consumer;

/**
 * @author tank198435163.com
 */
public class ConsumerHandler {

  public <T, E extends Exception> Consumer<T> consume(@Nonnull final ThrowingConsumer<T, E> consumer,
                                                      @Nonnull final Class<E> exClazz) {
    return data -> {
      try {
        consumer.consumeWithException(data, exClazz);
      } catch (Exception e) {
        E exp = exClazz.cast(e);
        throw new RuntimeException(exp);
      }
    };
  }

  public static void main(String[] args) {
    ConsumerHandler consumerHandler = new ConsumerHandler();
    Consumer<Integer> consumer = consumerHandler.consume(((data, ex) -> {
      int i = 1;
      int result = i / data;
      System.out.println(String.format("result = %d", result));
    }), ArithmeticException.class);

    Arrays.asList(1, 2, 3, 0).stream().forEach(consumer::accept);

  }

}

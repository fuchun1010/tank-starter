package com.tank.exception;

import javax.annotation.Nonnull;

/**
 * @author tank198435163.com
 */
@FunctionalInterface
public interface ThrowingConsumer<T, E extends Exception> {

  /**
   * 带有异常的消费
   *
   * @param data
   * @param ex
   * @throws Exception
   */
  void consumeWithException(@Nonnull final T data, @Nonnull final Class<E> ex) throws Exception;
}

package com.tank.listener;

import com.google.common.eventbus.Subscribe;
import lombok.NonNull;

import static java.lang.String.format;

public class OrderCreatedListener {

  @Subscribe
  public void done(@NonNull final String str) {
    System.out.println(format("thread = %s,content = %s", Thread.currentThread().getName(), str));
  }

}

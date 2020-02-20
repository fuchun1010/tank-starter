package com.tank.monitor;

import com.google.common.eventbus.Subscribe;
import lombok.NonNull;

public class FileListener {

  @Subscribe
  public void done(@NonNull final Item item) {
    System.out.println(item);
  }
}

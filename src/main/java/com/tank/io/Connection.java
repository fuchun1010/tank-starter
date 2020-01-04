package com.tank.io;

import com.tank.message.MessageBuffer;
import lombok.NonNull;

public abstract class Connection {

  public void requestClose() {
    this.shouldClose = true;
  }

  public boolean shouldClose() {
    return shouldClose;
  }

  protected abstract void write(@NonNull final byte[] data);

  protected abstract byte[] fetchData();

  protected volatile boolean shouldClose = false;

  protected MessageBuffer messageBuffer;


}

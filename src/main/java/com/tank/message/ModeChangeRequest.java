package com.tank.message;

import com.tank.io.NioSocketConnection;
import lombok.NonNull;

public class ModeChangeRequest {

  public ModeChangeRequest(@NonNull final NioSocketConnection connection, int ops) {
    this.connection = connection;
    this.ops = ops;
  }

  public NioSocketConnection connection;
  public int ops;
}

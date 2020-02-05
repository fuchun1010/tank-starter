package com.tank.wheel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerJob {

  private long id;

  private boolean status;

  private Schedual schedual;

  private int round;

  private int index;

  public boolean isReady() {
    return round == 0;
  }

  public void decrement() {
    if (round < 0) {
      throw new IllegalArgumentException("round less than zero");
    }
    --round;
  }

}

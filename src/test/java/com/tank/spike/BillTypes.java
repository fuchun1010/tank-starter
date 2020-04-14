package com.tank.spike;

import com.annimon.stream.Stream;

public enum BillTypes {

  POS(1, "pos"), B2C(2, "b2c"), O2O(3, "o2o"), EMPTY(Integer.MAX_VALUE, "empty");

  public int type() {
    return this.type;
  }

  public String desc() {
    return this.desc;
  }

  BillTypes(int type, String desc) {
    this.type = type;
    this.desc = desc;
  }


  public int min() {
    int minValue = Stream.of(BillTypes.values()).map(BillTypes::type).reduce(POS.type, Integer::min);
    return minValue;
  }

  public int max() {
    int minValue = Stream.of(BillTypes.values()).map(BillTypes::type).reduce(POS.type, Integer::max);
    return minValue;
  }


  private int type;

  private String desc;


}

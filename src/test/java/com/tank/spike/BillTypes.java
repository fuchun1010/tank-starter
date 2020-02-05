package com.tank.spike;

public enum BillTypes {

  POS(1, "pos"), B2C(2, "b2c"), O2O(3, "o2o");

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


  private int type;

  private String desc;


}

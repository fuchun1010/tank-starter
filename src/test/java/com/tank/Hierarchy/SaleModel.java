package com.tank.Hierarchy;

public enum SaleModel {

  ON_LINE(1, "线上"),
  OFF_LINE(2, "线下");

  SaleModel(int type, String desc) {
    this.type = type;
    this.desc = desc;
  }

  public int type() {
    return type;
  }

  public String desc() {
    return desc;
  }

  private int type;
  private String desc;
}

package com.tank.order;

public class PkApp {

  public static void main(String[] args) {
    PkSequence pkSequence = new PkSequence();

    pkSequence.generatePrimaryKey();

    new Thread(() -> {
      for (; ; ) {
        pkSequence.primaryKey();
      }
    }).start();

    pkSequence.monitorPrimaryKeySequence();

  }
}

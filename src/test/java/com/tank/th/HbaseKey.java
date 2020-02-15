package com.tank.th;

import org.junit.Test;

public class HbaseKey {


  @Test
  public void testKey() {
    long phone = 18623377391L;

    int yearMonth = 20200213;

    long rs = (phone ^ yearMonth) & 512 - 1;
    System.out.println(rs);

    System.out.println(System.nanoTime());
  }

}

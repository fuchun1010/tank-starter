package com.tank.ds;

import com.tank.ds.skip.MySkipList;

import java.util.concurrent.ThreadLocalRandom;

public class DsApp {
  public static void main(String[] args) {
    MySkipList<String> skipList = new MySkipList<>();
    ThreadLocalRandom random = ThreadLocalRandom.current();
    for (int i = 0; i < 100; i++) {
      int key = random.nextInt(100);
      skipList.put(key, String.valueOf(i));
    }
    skipList.pint();
    skipList.init();
  }
}

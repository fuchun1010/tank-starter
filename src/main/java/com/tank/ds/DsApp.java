package com.tank.ds;

import com.tank.ds.skip.SkipList;

public class DsApp {
  public static void main(String[] args) {
    SkipList<String> skipList = new SkipList<>();
    skipList.put(1, "1");
    skipList.put(10, "10");
    skipList.put(8, "8");
    skipList.put(5, "5");
    skipList.put(7, "7");
    skipList.print();
    skipList.clear();
  }
}

package com.tank.ds;

import com.tank.ds.skip.SkipList;

public class DsApp {
  public static void main(String[] args) {
    SkipList<String> skipList = new SkipList<>();
    System.out.println(skipList);
    skipList.put(1, "1");
    skipList.put(10, "10");
    skipList.put(8, "8");
    skipList.put(5, "5");
    System.out.println(skipList);
    System.out.println(skipList.size());
  }
}

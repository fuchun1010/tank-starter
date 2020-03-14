package com.tank.ds;

import com.tank.ds.skip.SkipList2;

public class DsApp {
  public static void main(String[] args) {
    SkipList2<String> skipList = new SkipList2<>();
    System.out.println(skipList);
    skipList.put(6, "cn");
    skipList.put(1, "https");
    skipList.put(2, ":");
    skipList.put(3, "//");
    skipList.put(1, "http");
    skipList.put(4, "jetchen");
    skipList.put(5, ".");
    System.out.println(skipList);
    System.out.println(skipList.size());
  }
}

package com.tank.ds;

import com.tank.ds.skip.SkipList2;

public class DsApp {
  public static void main(String[] args) {
    SkipList2<String> list = new SkipList2<>();
    System.out.println(list);
    list.put(6, "cn");
    list.put(1, "https");
    list.put(2, ":");
    list.put(3, "//");
    list.put(1, "http");
    list.put(4, "jetchen");
    list.put(5, ".");
    System.out.println(list);
    System.out.println(list.size());
  }
}

package com.tank.ds.skip.my;

public class SkipApp {

  public static void main(String[] args) {
    MySkipList<String> skipList = new MySkipList<>();
    skipList.put(1, "1");
    skipList.put(10, "10");
//    skipList.put(8, "8");
//    skipList.put(5, "5");
    skipList.print();
  }

}

package com.tank.ds.skip.my;

public class SkipApp {

  public static void main(String[] args) {
    MySkiptList<String> skipList = new MySkiptList<>();
    skipList.put(1, "1");
    skipList.put(10, "10");
    skipList.put(8, "8");
    skipList.print();
  }

}

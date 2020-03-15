package com.tank.ds.skip.my;

import java.util.concurrent.ThreadLocalRandom;

import static com.tank.ds.skip.my.Item.HEAD;
import static com.tank.ds.skip.my.Item.TAIL;

public class MySkipList<T> {

  public MySkipList() {
    this.head = new Item<>(HEAD, null);
    this.tail = new Item<>(TAIL, null);
    this.horizontalDoubleLink(this.head, this.tail);
  }

  public void put(int key, T data) {
    Item<T> p = this.findInsertPosition(key);
    if (p == null) {
      System.out.println("debugger");
    }
    if (p.key.compareTo(key) == 0) {
      p.data = data;
      return;
    }
    Item<T> newItem = new Item<>(key, data);
    backDoubleLink(p, newItem);

    int currentLevel = 0;
    //计算概率
//    while (rate <= random.nextInt(100) && currentLevel < MAX_LEVEL) {
//
//      if ("10".equalsIgnoreCase(data.toString())) {
//        System.out.println("debugger2");
//      }
//
//
//      if (currentLevel >= listLevel) {
//        listLevel++;
//        Item<T> p1 = new Item<>(HEAD, null);
//        Item<T> p2 = new Item<>(HEAD, null);
//        horizontalDoubleLink(p1, p2);
//        verticalDoubleLink(p1, head);
//        verticalDoubleLink(p2, tail);
//        head = p1;
//        tail = p2;
//      }
//
//      while (p.up == null) {
//        p = p.left;
//      }
//
//      p = p.up;
//      Item<T> tmp = new Item<>(newItem.key, null);
//      backDoubleLink(p, tmp);
//      verticalDoubleLink(tmp, newItem);
//      newItem = tmp;
//      currentLevel++;
//    }

//    System.out.println("key = " + key + ", data = " + data);
  }

  private void verticalDoubleLink(Item<T> p1, Item<T> p2) {
    p1.download = p2;
    p2.up = p1;
  }

  public void print() {
    Item<T> cursor = this.head;

    while (cursor.download != null) {
      cursor = cursor.download;
    }


    while (true) {
      if (cursor.data != null) {
        System.out.println(cursor.data);
      }
      cursor = cursor.right;
      if (cursor == null || cursor.data == null) {
        break;
      }
    }
  }

  /**
   * @param first
   * @param second
   */
  private void horizontalDoubleLink(Item<T> first, Item<T> second) {
    first.right = second;
    second.left = first;
  }

  /**
   * newItem 插入到p后面
   *
   * @param newItem
   * @param p
   */
  private void backDoubleLink(final Item<T> p, final Item<T> newItem) {

    newItem.left = p;
    newItem.right = p.right;
    p.right.left = newItem;
    p.right = newItem;

  }

  private Item<T> findInsertPosition(int key) {
    Item p = this.head;

    for (; ; ) {

      while (p.right.key != TAIL && p.right.key < key) {

        p = p.right;

      }
      if (p.download != null) {
        p = p.download;
      } else {
        break;
      }

    }

    return p;



  }

  private Item<T> head, tail;

  private int rate = 25;

  private final int MAX_LEVEL = 32;

  private ThreadLocalRandom random = ThreadLocalRandom.current();

  private int listLevel;
}

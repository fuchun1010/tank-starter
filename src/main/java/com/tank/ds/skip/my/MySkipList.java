package com.tank.ds.skip.my;

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
    if (p.key.compareTo(key) == 0) {
      p.data = data;
      return;
    }
    Item<T> newItem = new Item<>(key, data);
    backDoubleLink(p, newItem);
    System.out.println("key = " + key + ", data = " + data);
  }

  public void print() {
    Item<T> cursor = this.head;
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
}

package com.tank.ds.skip.my;

import static com.tank.ds.skip.my.Item.HEAD;
import static com.tank.ds.skip.my.Item.TAIL;

public class MySkiptList<T> {

  public MySkiptList() {
    this.head = new Item<>(HEAD, null);
    this.tail = new Item<>(TAIL, null);
    this.horizontalDoubleLink(this.head, this.tail);
  }

  /**
   * @param first
   * @param second
   */
  private void horizontalDoubleLink(Item<T> first, Item<T> second) {
    first.right = second;
    second.left = first;
  }

  public void put(int key, T data) {
    Item<T> p = this.findNode(key);
    if (p.key.compareTo(key) == 0) {
      p.data = data;
      return;
    }
    Item<T> newItem = new Item<>(key, data);
    backDoubleLink(newItem, p);
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
   * newItem 插入到p后面
   *
   * @param newItem
   * @param p
   */
  private void backDoubleLink(final Item<T> newItem, final Item<T> p) {
    newItem.right = p.right;
    newItem.left = p;
    p.right.left = newItem;
    p.right = newItem;
  }

  private Item<T> findNode(int key) {
    Item<T> p = this.head;

    while (true) {
      while (true) {
        boolean isOk = p.right.key != TAIL && p.right.key < key;
        if (!isOk) {
          break;
        }
        p = p.right;
      }
      if (p.bottom != null) {
        p = p.bottom;
      }
      break;
    }

    return p;
  }

  private Item<T> head, tail;
}

package com.tank.ds.skip;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @param <T>
 * @author tank198435163.com
 */
public class MySkipList<T> {

  public MySkipList() {
    this.head = new Node<>(Node.HEAD_KEY, null);
    this.tail = new Node<>(Node.TAIL_KEY, null);
    this.horizontalDLink(this.head, this.tail);
    this.listLevel = 0;
  }

  public void put(int k, T value) {
    Node<T> p = this.findOne(k);
    if (p.key == k) {
      p.value = value;
      return;
    }
    Node<T> q = new Node<>(k, value);
    backDLink(p, q);
    
  }


  private void backDLink(Node<T> node1, Node<T> node2) {
    node2.left = node1;
    node2.right = node1.right;
    node1.right.left = node2;
    node1.right = node2;
  }

  public Node<T> findOne(final int k) {
    Node<T> p = this.head;

    while (true) {

      while (p.right.key != Node.TAIL_KEY && p.right.key < k) {
        p = p.right;
      }
      if (p.down != null) {
        p = p.down;
      } else {
        break;
      }
    }

    return p;
  }

  /**
   * @param p1
   * @param p2
   */
  private void horizontalDLink(Node<T> p1, Node<T> p2) {
    p1.right = p2;
    p2.left = p1;
  }

  public void pint() {
    Node<T> p = this.head;

    while (p.getValue() == null) {
      p = p.right;
    }

    for (; ; ) {
      System.out.println(p.getValue());
      p = p.right;
      if (p.key == Node.TAIL_KEY) {
        break;
      }
    }

  }


  private final int rate = 25;

  private ThreadLocalRandom random = ThreadLocalRandom.current();

  private int listLevel = 0;

  private int MAX_LEVEL = 32;

  private Node<T> head, tail;

}

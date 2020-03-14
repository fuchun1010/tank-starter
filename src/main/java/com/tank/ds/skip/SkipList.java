package com.tank.ds.skip;

import lombok.SneakyThrows;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class SkipList<D extends Comparable<D>, T> {

  public SkipList() {
    this.maxHeight = 3;
  }

  public SkipList(int maxHeight) {
    super();
    this.maxHeight = maxHeight;
  }

  @SneakyThrows
  public Node<D, T> addNode(Node<D, T> node) {

    if (this.isEmptyHeader()) {
      this.header = this.p = this.tail = node;
      //
      for (int i = 0; i < maxHeight; i++) {
        Node<D, T> newNode = new Node<>();
        newNode.data = node.data;
        this.p.top = newNode;
        newNode.bottom = this.p;
        this.p = newNode;
      }
      this.levelTail = this.p;
    } else {
      Node<D, T> target = this.findInsertPosition(node.data);
      node.right = target.right;
      if (target.right != null) {
        target.right.left = node;
      }
      target.right = node;
      node.left = target;
      this.p = node;
    }

    int newLevel = this.update2NLevel();

    for (int i = 0; i < newLevel; i++) {
      Node<D, T> newNode = new Node<>();
      newNode.data = node.data;
      this.p.top = newNode;
      newNode.bottom = this.p;
      this.p = newNode;
      Node<D, T> levelNode = this.findNLevelNode(node, i + 1);
      if (Objects.nonNull(levelNode)) {
        levelNode.right = newNode;
        newNode.left = levelNode;
      }
    }
    this.p = this.header;
    if (this.tail.getData().compareTo(node.getData()) < 0) {
      this.tail = node;
    }
    return this.header;

  }

  public Node<D, T> getHeader() {
    return this.header;
  }

  private Node<D, T> findNLevelNode(Node<D, T> node, int newLevel) {
    //TODO some bug
    Node<D, T> tmp = node.left;
    if (Objects.isNull(tmp)) {
      return null;
    }
    for (int i = 0; i < newLevel; i++) {
      tmp = tmp.top;
      if (Objects.isNull(tmp)) {
        return null;
      }
    }
    return tmp;
  }

  public void print() {

    Node<D, T> cursor = this.header;

    while (cursor != null) {
      System.out.println(cursor.getData().toString());
      cursor = cursor.right;
    }

  }


  private Node<D, T> searchBottomNode() {

    Node<D, T> c = this.p;

    for (; ; ) {
      if (c.bottom == null) {
        return c;
      }
      c = c.bottom;
    }

  }

  private Node<D, T> findInsertPosition(D data) {
    Node<D, T> tmp = this.tail;


    for (; ; ) {
      int result = tmp.getData().compareTo(data);
      if (result < 0) {

        return tmp;
      }
      tmp = tmp.left;
    }
  }

  private int heightOfNode() {
    int sum = 0;
    Node<D, T> tmp = this.p.top;
    while (true) {
      if (tmp == null) {
        break;
      }
      tmp = tmp.top;
    }
    return sum;
  }

  private Node<D, T> findMostTopNode() {

    return null;
  }

  private int update2NLevel() {
    return random.nextInt(seed);
  }

  private boolean isEmptyHeader() {
    return Objects.isNull(this.header);
  }


  private Node<D, T> header, p, tail, levelTail;


  private int maxHeight;

  private ThreadLocalRandom random = ThreadLocalRandom.current();

  private int seed = 3;
}

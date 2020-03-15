package com.tank.ds.skip;

import java.util.Random;

public class SkipList<T> {

  public SkipList() {
    random = new Random();
    clear();
  }

  /**
   * @Description: 清空跳跃表
   * @Param: []
   * @return: void
   * @Date: 2019/9/16 17:41
   */
  public void clear() {
    head = new Node(Node.HEAD_KEY, null);
    tail = new Node(Node.TAIL_KEY, null);
    horizontalLink(head, tail);
    listLevel = 0;
    nodes = 0;
  }

  public boolean isEmpty() {
    return nodes == 0;
  }

  public int size() {
    return nodes;
  }

  /**
   * @Description: 找到要插入的位置前面的那个key 的最底层节点
   * @Param: [key]
   * @Date: 2019/9/16 17:42
   */
  private Node<T> findNode(int key) {
    Node<T> p = head;
    while (true) {
      while (p.right.getKey() != Node.TAIL_KEY && p.right.getKey() <= key) {
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
   * @Description: 查找是否存在key，存在则返回该节点，否则返回null
   * @Param: [key]
   */
  public Node<T> search(int key) {
    Node<T> p = findNode(key);
    if (key == p.getKey()) {
      return p;
    } else {
      return null;
    }
  }

  /**
   * @Description: 向跳跃表中添加key-value
   * @Param: [k, v]
   * @return: void
   */
  public void put(int k, T v) {
    Node<T> p = findNode(k);
    // 如果key值相同，替换原来的value即可结束
    if (k == p.getKey()) {
      p.setValue(v);
      return;
    }
    Node<T> q = new Node<>(k, v);
    backLink(p, q);
    int currentLevel = 0; // 当前所在的层级是0
    // 计算概率
    while (random.nextDouble() < PROBABILITY && currentLevel < MAX_LEVEL) {
      // 如果超出了高度，需要重新建一个顶层
      if (currentLevel >= listLevel) {
        listLevel++;
        Node<T> p1 = new Node<>(Node.HEAD_KEY, null);
        Node<T> p2 = new Node<>(Node.TAIL_KEY, null);
        horizontalLink(p1, p2);
        verticalLink(p1, head);
        verticalLink(p2, tail);
        head = p1;
        tail = p2;
      }
      // 将p移动到上一层
      while (p.up == null) {
        p = p.left;
      }
      p = p.up;

      Node<T> e = new Node<>(k, null); // 只保存key就ok
      backLink(p, e); // 将e插入到p的后面
      verticalLink(e, q); // 将e和q上下连接
      q = e;
      currentLevel++;
    }
    nodes++; // 层数递增
  }

  /**
   * @Description: node1后面插入node2
   * @Param: [node1, node2]
   * @return: void
   * @Date: 2019/9/16 17:45
   */
  private void backLink(Node<T> node1, Node<T> node2) {
    node2.left = node1;
    node2.right = node1.right;
    node1.right.left = node2;
    node1.right = node2;
  }

  /**
   * @Description: 水平双向连接
   * @Param: [node1, node2]
   * @return: void
   * @Date: 2019/9/16 17:45
   */
  private void horizontalLink(Node<T> node1, Node<T> node2) {
    node1.right = node2;
    node2.left = node1;
  }

  /**
   * @Description: 垂直双向连接
   * @Param: [node1, node2]
   * @return: void
   * @Date: 2019/9/16 17:45
   */
  private void verticalLink(Node<T> node1, Node<T> node2) {
    node1.down = node2;
    node2.up = node1;
  }

  @Override
  public String toString() {
    if (isEmpty()) {
      return "跳跃表为空！";
    }
    StringBuilder builder = new StringBuilder();
    Node<T> p = head;
    while (p.down != null) {
      p = p.down;
    }

    while (p.left != null) {
      p = p.left;
    }
    if (p.right != null) {
      p = p.right;
    }
    while (p.right != null) {
      builder.append(p).append("\n");
      p = p.right;
    }

    return builder.toString();
  }

  public void print() {
    Node<T> cursor = this.head;
    while (cursor.down != null) {
      cursor = cursor.down;
    }

    for (; ; ) {
      cursor = cursor.right;
      if (cursor == null) {
        break;
      }
      if (cursor.getValue() == null) {
        continue;
      }
      System.out.println(cursor.getValue());
    }
  }

  private Node<T> head, tail;
  private int nodes; // 节点总数
  private int listLevel; // 最大层数
  private Random random; // 随机数，用于投掷硬币决定是否要加层高
  private static final double PROBABILITY = 0.25;
  private static final int MAX_LEVEL = 32;
}

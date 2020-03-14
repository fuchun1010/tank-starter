package com.tank.ds;

import com.tank.ds.skip.Node;
import com.tank.ds.skip.SkipList;

public class DsApp {
  public static void main(String[] args) {
    SkipList<Integer, Integer> skipList = new SkipList<>(3);
    Node<Integer, Integer> node1 = new Node<>(2);
    Node<Integer, Integer> node5 = new Node<>(5);
    Node<Integer, Integer> node3 = new Node<>(3);
    Node<Integer, Integer> node4 = new Node<>(4);
    skipList.addNode(node1);
    skipList.addNode(node5);
    skipList.addNode(node3);
    skipList.addNode(node4);
    Node<Integer, Integer> header = skipList.getHeader();
    System.out.println("header = ");
  }
}

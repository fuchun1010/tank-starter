package com.tank.ds.skip;

import com.google.common.base.MoreObjects;
import lombok.Getter;
import lombok.Setter;

/**
 * @param <D>
 * @param <T>
 * @author tank198435163.com
 */
@Getter
@Setter

public class Node<D extends Comparable<D>, T> {

  public Node() {

  }

  public Node(final D data) {
    this.data = data;
  }

  public D data;

  public Node<D, T> top;

  public Node<D, T> bottom;

  public Node<D, T> left;

  public Node<D, T> right;

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("data:", this.data).toString();
  }
}

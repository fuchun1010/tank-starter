package com.tank.ds.skip.my;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = {"key"})
public class Item<T> {

  public Item(int key, T data) {
    this.key = key;
    this.data = data;
  }

  public final static int HEAD = Integer.MIN_VALUE;

  public final static int TAIL = Integer.MAX_VALUE;

  public Item up, bottom, left, right;

  public Integer key;

  public T data;
}

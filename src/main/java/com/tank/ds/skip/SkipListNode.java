package com.tank.ds.skip;

import com.google.common.base.MoreObjects;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description: 跳跃表的节点, 包括key-value和上下左右4个指针
 * @Author: Jet.Chen
 * @Date: 2019/9/16 17:48
 */
public class SkipListNode<T> {

  public SkipListNode(int k, T v) {
    key = k;
    value = v;
  }

  public int getKey() {
    return key;
  }

  public void setKey(int key) {
    this.key = key;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null) {
      return false;
    }
    if (!(o instanceof SkipListNode<?>)) {
      return false;
    }
    SkipListNode<T> ent;
    try {
      ent = (SkipListNode<T>) o; // 检测类型       
    } catch (ClassCastException ex) {
      return false;
    }
    return (ent.getKey() == key) && (ent.getValue() == value);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("key-value:", this.key)
        .add("-", this.value)
        .toString();
  }

  private int key;
  @Getter
  @Setter
  private T value;
  public SkipListNode<T> up, down, left, right; // 上下左右 四个指针   
  public static final int HEAD_KEY = Integer.MIN_VALUE; // 负无穷   
  public static final int TAIL_KEY = Integer.MAX_VALUE; // 正无穷   


}


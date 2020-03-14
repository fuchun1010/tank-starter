package com.tank.ds.skip;

import com.google.common.base.MoreObjects;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description: 跳跃表的节点, 包括key-value和上下左右4个指针
 * @Date: 2019/9/16 17:48
 */

@EqualsAndHashCode(of = {"key"})
public class Node<T> {

  public Node(int k, T v) {
    key = k;
    value = v;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("key-value:", this.key)
        .add("-", this.value)
        .toString();
  }

  @Getter
  private int key;
  @Getter
  @Setter
  private T value;
  public Node<T> up, down, left, right; // 上下左右 四个指针   
  public static final int HEAD_KEY = Integer.MIN_VALUE; // 负无穷   
  public static final int TAIL_KEY = Integer.MAX_VALUE; // 正无穷   


}


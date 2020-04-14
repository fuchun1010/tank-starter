package com.tank.Hierarchy;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Accessors(chain = true)
public class Node {

  public void add(Node node) {
    this.lists.add(node);
  }

  public boolean isLeaf() {
    return this.lists.isEmpty();
  }

  @Getter
  @Setter
  private int type;

  @Getter
  @Setter
  private String desc;

  @Getter
  private List<Node> lists = new ArrayList<>(2 << 4);
}

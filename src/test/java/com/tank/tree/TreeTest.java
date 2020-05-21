package com.tank.tree;

import com.google.common.collect.Lists;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.junit.Test;

import java.util.List;

public class TreeTest {

  @Test
  public void testAdd() {
    Parent root = new Parent();
    root.setDesc("m0001");
    root.setCode("m-p0001");

    Child child1 = new Child();
    child1.setCode("s0001");
    child1.setDesc("l-s0001");

    Parent right = new Parent();
    right.setDesc("r0001");
    right.setCode("r-p0001");

    Child child2 = new Child();
    child2.setCode("s0002");
    child2.setDesc("r-s0002");

    right.add(child2);


    root.add(child1);
    root.add(right);

    root.print();
  }

  @Getter
  @Setter
  @EqualsAndHashCode(of = "code")
  private abstract class Node {

    private String desc;

    private String code;

  }

  @Getter
  @Setter
  public class Parent extends Node {

    public <T extends Node> void add(@NonNull final T item) {
      this.children.add(item);
    }

    public <T extends Node> void print() {
      this.print(this);
    }

    private <T extends Node> void print(@NonNull final T item) {

      if (item instanceof Child) {
        System.out.println("item = " + item.getDesc());
      } else {
        Parent tmp = ((Parent) item);
        System.out.println("item = " + tmp.getDesc());
        for (Node child : tmp.getChildren()) {
          System.out.println("item = " + child.getDesc());
          this.print(child);
        }
      }


    }


    private List<Node> children = Lists.newLinkedList();

  }


  public class Child extends Node {
  }

}

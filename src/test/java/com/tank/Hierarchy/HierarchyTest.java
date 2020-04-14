package com.tank.Hierarchy;

import com.google.common.collect.Queues;
import org.junit.Test;

import java.util.Queue;

import static com.tank.Hierarchy.SaleModel.OFF_LINE;

public class HierarchyTest {

  @Test
  public void test1() {
    OrderHierarchy orderHierarchy = new OrderHierarchy();
    Queue<Integer> queue = Queues.newArrayDeque();
    queue.add(OFF_LINE.type());
    queue.add(OFF_LINE.type());
    queue.add(9);
    queue.add(7);
    queue.add(7);

    int result = orderHierarchy.findLeaf(queue);
    System.out.println(result);
  }

}

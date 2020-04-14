package com.tank.Hierarchy;

import java.util.List;
import java.util.Queue;

import static com.tank.Hierarchy.SaleModel.OFF_LINE;

public class OrderHierarchy extends Node {

  public OrderHierarchy() {
    super();
    this.init();
  }

  private void init() {

    //上线线下
    OrderLevel offLine = new OrderLevel();
    offLine.setDesc("offLine").setType(OFF_LINE.type());

    //线下订单模式
    OrderLevel offLineModel = new OrderLevel();
    offLineModel.setDesc(OFF_LINE.desc()).setType(OFF_LINE.type());

    offLine.add(offLineModel);

    //线下订单类型
    OrderLevel offLineOrderType = new OrderLevel();
    offLineOrderType.setType(9).setDesc("线下");
    offLineModel.add(offLineOrderType);

    //线下订单渠道
    OrderLevel offLineOrderChannel = new OrderLevel();
    offLineOrderChannel.setType(7).setDesc("线下");
    offLineOrderType.add(offLineOrderChannel);

    //线下配送类型
    OrderLevel offLineDispatch = new OrderLevel();
    offLineOrderChannel.setType(7).setDesc("无");
    offLineOrderChannel.add(offLineDispatch);

    this.add(offLine);

  }

  public int findLeaf(Queue<Integer> queue) {
    return this.findLeaf(this, queue);
  }

  private int findLeaf(Node root, Queue<Integer> queue) {

    int type = queue.poll();

    boolean isOver = root.isLeaf() && root.getType() == type;

    if (isOver) {
      return root.getType();
    }

    List<Node> nodes = root.getLists();

    for (Node node : nodes) {

      if (node.getType() == type) {
        for (Node tmp : node.getLists()) {
          int result = this.findLeaf(tmp, queue);
          if (result != -1) {
            return result;
          }
        }
      }
    }

    return -1;

  }

}


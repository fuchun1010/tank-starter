package com.tank.splitter;

import com.google.common.collect.Lists;
import lombok.NonNull;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class WeightFilter extends OrderSpittedFilter<Order> {

  public WeightFilter(@NonNull OrderGenerator orderGenerator) {
    super(orderGenerator);
  }

  @Override
  List<Order> doFilter(@NonNull Order order) {
    Set<Item> items = order.getItems();
    List<Order> orders = Lists.newLinkedList();
    Item[] itemArr = new Item[items.size()];
    itemArr = items.toArray(itemArr);

    Arrays.sort(itemArr, (a, b) -> a.getWeight() >= b.getWeight() ? 1 : -1);

    int weight = 0;

    Order tmpOrder = this.createNewOrder(order);

    for (Item item : itemArr) {
      boolean isOk = item.getWeight() + weight <= maxWeight ? true : false;
      if (!isOk) {
        orders.add(tmpOrder);
        tmpOrder = this.createNewOrder(order);
        weight = 0;
      }
      weight += item.getWeight();
      tmpOrder.addItem(item);
    }

    if (!tmpOrder.isEmpty()) {
      orders.add(tmpOrder);
    }

    return orders;
  }

  private int maxWeight = 20;
}

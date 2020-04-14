package com.tank.goods;

import com.google.common.collect.Sets;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

public class OrderSplitterHandler {


  public <T> Collection<SimpleOrder> splitBy(Collection<SimpleOrder> simpleOrders, Function<SimpleOrder, Map<T, List<SimpleItem>>> function) {
    Collection<SimpleOrder> orders = Sets.newConcurrentHashSet();
    SimpleOrder tmp;
    for (SimpleOrder simpleOrder : simpleOrders) {
      Map<T, List<SimpleItem>> items = function.apply(simpleOrder);
      for (Map.Entry<T, List<SimpleItem>> entry : items.entrySet()) {
        tmp = new SimpleOrder();
        tmp.setOrderId(UUID.randomUUID().toString());
        tmp.setItems(entry.getValue());
        orders.add(tmp);
      }
    }

    return orders;
  }

}

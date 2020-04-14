package com.tank.goods;

import com.google.common.collect.Sets;
import lombok.NonNull;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author tank198435163.com
 */
public class StockSplitter implements OrderSplitter {

  @Override
  public Collection<SimpleOrder> splitOrderBy(@NonNull Collection<SimpleOrder> simpleOrders) {
    Collection<SimpleOrder> orders = Sets.newConcurrentHashSet();
    SimpleOrder tmp;
    for (SimpleOrder simpleOrder : simpleOrders) {
      Map<String, List<SimpleItem>> items = simpleOrder
          .getItems()
          .stream()
          .collect(Collectors.groupingBy(SimpleItem::getStock));

      for (Map.Entry<String, List<SimpleItem>> entry : items.entrySet()) {
        tmp = new SimpleOrder();
        tmp.setOrderId(UUID.randomUUID().toString());
        tmp.setItems(entry.getValue());
        orders.add(tmp);
      }
    }

    return orders;
  }
}

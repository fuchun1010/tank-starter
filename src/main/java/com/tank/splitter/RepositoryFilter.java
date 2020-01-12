package com.tank.splitter;

import com.google.common.collect.Lists;
import lombok.NonNull;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class RepositoryFilter extends OrderSpittedFilter<Order> {


  public RepositoryFilter(@NonNull OrderGenerator orderGenerator) {
    super(orderGenerator);
  }

  @Override
  List<Order> doFilter(@NonNull Order order) {
    Set<Item> items = order.getItems();
    Map<String, List<Item>> result = items.stream().collect(Collectors.groupingBy(item -> item.getRepositoryCode()));
    Order tmp;
    List<Order> orders = Lists.newLinkedList();
    for (String repository : result.keySet()) {
      tmp = new Order();
      tmp.setOrderNo(this.newOrderNo());
      result.get(repository).stream().forEach(tmp::addItem);
      tmp.setParentOrderNo(order.getOrderNo());
      orders.add(tmp);
    }
    return orders;
  }
}

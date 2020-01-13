package com.tank.splitter;

import com.google.common.collect.Lists;
import lombok.NonNull;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class OrderSplitterChain {


  public List<Order> doFilter(@NonNull final Order order, int index, List<Order> tmpResult) {

    if (index == this.filters.size()) {
      globalOrders.add(tmpResult.get(0));
      return globalOrders;
    }

    OrderSpittedFilter filter = this.filters.get(index);

    index++;

    Order target = tmpResult.isEmpty() ? order : tmpResult.get(0);

    List<Order> orders = filter.doFilter(target);

    for (Order child : orders) {
      doFilter(child, index, Arrays.asList(child));
    }


    return globalOrders;

  }


  public OrderSplitterChain add(@NonNull final OrderSpittedFilter orderSpittedFilter) {
    this.filters.add(orderSpittedFilter);
    return this;
  }

  private List<OrderSpittedFilter> filters = Lists.newLinkedList();


  private CopyOnWriteArrayList<Order> globalOrders = new CopyOnWriteArrayList();
}

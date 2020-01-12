package com.tank.splitter;

import lombok.NonNull;

import java.util.List;

public abstract class OrderSpittedFilter<T extends Order> {

  public OrderSpittedFilter(@NonNull final OrderGenerator orderGenerator) {
    this.orderGenerator = orderGenerator;
  }

  public long newOrderNo() {
    return this.orderGenerator.generateOrderNo();
  }

  public Order createNewOrder(@NonNull final Order order) {
    Order newOrder = new Order();
    newOrder.setParentOrderNo(order.getParentOrderNo());
    newOrder.setOrderNo(this.newOrderNo());
    return newOrder;
  }

  abstract List<T> doFilter(@NonNull final T order);

  private OrderGenerator orderGenerator;

}

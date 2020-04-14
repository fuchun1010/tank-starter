package com.tank.Hierarchy.second;

import lombok.NonNull;

import java.util.Optional;
import java.util.function.Function;

public enum OrderType {

//  POS("1-1-1-1") {
//    @Override
//    int calculateOrderType() {
//      return 15;
//    }
//  }, ENJOY("1-2-1-2") {
//    @Override
//    int calculateOrderType() {
//      return 14;
//    }
//  };

  POS("1-1-1-1") {
    @Override
    String doneBusiness(Order data, Function<Order,String> function) {
      return  function.apply(data);
    }
  };


  public Optional<OrderType> toOrderType(@NonNull final Order order) {
    return Optional.ofNullable(OrderType.valueOf(order.toKey()));
  }


  OrderType(String key) {
    this.key = key;
  }

  private String key;

  abstract String doneBusiness(Order data, Function<Order,String> function);


}

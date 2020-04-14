package com.tank.Hierarchy.second;

import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.function.Function;

@Getter
@Setter
public class Order {

  private int onlineOrOffLine = 1;

  private int orderType = 1;

  private int channel = 1;

  private int dispatch = 1;

  public String toKey() {
    return String.format("%d-%d-%d-%d", onlineOrOffLine, orderType, channel, dispatch);
  }

  public void xx(Order order) {

  }


  public String sayHello(Order order) {
    return "hello";
  }

  public Map<OrderType, Function<Order, String>> init() {
    Map<OrderType, Function<Order, String>> map = Maps.newHashMap();
    map.putIfAbsent(OrderType.POS, d -> "hello");
    return map;
  }

}

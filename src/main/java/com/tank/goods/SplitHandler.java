package com.tank.goods;

import lombok.NonNull;

import java.util.Collection;

public  class SplitHandler implements OrderSplitter {
  @Override
  public Collection<SimpleOrder> splitOrderBy(@NonNull Collection<SimpleOrder> simpleOrders) {
    return null;
  }
}

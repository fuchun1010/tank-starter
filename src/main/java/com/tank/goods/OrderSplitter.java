package com.tank.goods;

import lombok.NonNull;

import java.util.Collection;

public interface OrderSplitter {


  Collection<SimpleOrder> splitOrderBy(@NonNull final Collection<SimpleOrder> simpleOrders);

}

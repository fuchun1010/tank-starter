package com.tank.goods;

import com.google.common.collect.Sets;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Collection;

/**
 * @author tank198435163.com
 */
@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode(of = "orderId")
public class SimpleOrder {

  public void addSubOrder(@NonNull final SimpleOrder simpleOrder) {
    this.subOrders.add(simpleOrder);
  }

  public void addSimpleItem(@NonNull final SimpleItem simpleItem) {
    this.items.add(simpleItem);
  }

  private String orderId;

  private Collection<SimpleItem> items = Sets.newConcurrentHashSet();

  private Collection<SimpleOrder> subOrders = Sets.newConcurrentHashSet();


}

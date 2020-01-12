package com.tank.splitter;

import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class Order {

  public <I extends Item> void addItem(@NonNull final I item) {
    this.items.add(item);
  }

  private long parentOrderNo = -1;

  private long orderNo;

  private Set<Item> items = Sets.newHashSet();

  public void addFilter(OrderSpittedFilter filter) {
    this.previousFilters.add(filter);
  }

  public boolean contains(OrderSpittedFilter filter) {
    return this.previousFilters.contains(filter);
  }

  public int filterNum() {
    return previousFilters.size();
  }


  public Set<OrderSpittedFilter> previousFilters = Sets.newHashSet();

  public boolean isEmpty() {
    return items.isEmpty();
  }
}

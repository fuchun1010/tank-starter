package com.tank.splitter;

import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

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


  @Override
  public String toString() {
    List<String> desc = this.items.stream().map(item -> item.getDesc()).collect(Collectors.toList());
    StringJoiner joiner = new StringJoiner(",");
    desc.forEach(joiner::add);
    return joiner.toString();
  }
}

package com.tank.splitter;

import com.annimon.stream.Stream;
import com.google.common.collect.Sets;
import lombok.NonNull;
import lombok.val;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Set;

public class OrderSplitterChainTest {

  @Test
  public void doFilter() {

    OrderGenerator orderGenerator = new OrderGenerator();
    Order order = this.mockOrder(orderGenerator);
    Assert.assertTrue(order.getItems().size() == 5);
    OrderSplitterChain orderSplitterChain = new OrderSplitterChain();
    OrderSplitterChain chain = orderSplitterChain
        .add(new RepositoryFilter(orderGenerator))
        .add(new WeightFilter(orderGenerator));
    val results = chain.doFilter(order, 0, new ArrayList<>());
    Assert.assertTrue(results.size() == 4);
    System.out.println(results.size());
  }

  @Test
  public void testRepositoryRule() {
    OrderGenerator orderGenerator = new OrderGenerator();
    Order order = this.mockOrder(orderGenerator);
    Assert.assertTrue(order.getItems().size() == 5);
    OrderSplitterChain orderSplitterChain = new OrderSplitterChain();
    OrderSplitterChain chain = orderSplitterChain
        .add(new RepositoryFilter(orderGenerator));
    val results = chain.doFilter(order, 0, new ArrayList<>());
    Assert.assertTrue(results.size() == 2);

  }

  @Test
  public void testWeightRule() {
    OrderGenerator orderGenerator = new OrderGenerator();
    Order order = this.mockOrder(orderGenerator);
    Assert.assertTrue(order.getItems().size() == 5);
    OrderSplitterChain orderSplitterChain = new OrderSplitterChain();
    OrderSplitterChain chain = orderSplitterChain
        .add(new WeightFilter(orderGenerator));
    val results = chain.doFilter(order, 0, new ArrayList<>());
    Assert.assertTrue(results.size() == 4);
  }

  private Item createItem(int itemId, String desc, String repositoryCode, int type, int weight) {
    Item item_01 = new Item();
    item_01.setId(itemId);
    item_01.setDesc(desc);
    item_01.setWeight(weight);
    item_01.setRepositoryCode(repositoryCode);
    item_01.setType(type);
    return item_01;
  }


  private Order mockOrder(@NonNull final OrderGenerator orderGenerator) {

    Item item_01 = this.createItem(1, "黄元帅苹果", "023", common, 10);
    Item item_02 = this.createItem(2, "梨子", "023", common, 15);
    Item item_03 = this.createItem(3, "香蕉", "0875", common, 8);
    Item item_04 = this.createItem(4, "哈密瓜", "0875", enjoy, 10);
    Item item_05 = this.createItem(5, "榴莲", "0875", enjoy, 15);

    long orderNo = orderGenerator.generateOrderNo();

    Order order = new Order();
    order.setOrderNo(orderNo);

    Stream.of(item_01, item_02, item_03, item_04, item_05).forEach(order::addItem);
    return order;
  }


  private int enjoy = 3;

  private int predicate = 2;

  private int common = 1;

  private Set<Item> items = Sets.newHashSet();
}
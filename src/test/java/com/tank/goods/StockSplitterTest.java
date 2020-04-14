package com.tank.goods;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@RunWith(MockitoJUnitRunner.class)
public class StockSplitterTest {

  @Test
  public void splitOrderBy() {

    StockSplitter stockSplitter = new StockSplitter();
    Collection<SimpleOrder> simpleOrders = stockSplitter.splitOrderBy(Arrays.asList(simpleOrder));
    Assert.assertTrue(simpleOrders.size() == 2);
  }

  @Test
  public void splitOrderByStock() {
    OrderSplitterHandler orderSplitterHandler = new OrderSplitterHandler();
    Collection<SimpleOrder> simpleOrdersByStock = orderSplitterHandler.splitBy(Arrays.asList(simpleOrder), d -> d.getItems().stream().collect(Collectors.groupingBy(SimpleItem::getStock)));
    Collection<SimpleOrder> simpleOrderByDispatchWay = orderSplitterHandler.splitBy(Arrays.asList(simpleOrder), d -> d.getItems().stream().collect(Collectors.groupingBy(SimpleItem::getDispatchWay)));

    Collection<SimpleOrder> result = orderSplitterHandler.splitBy(simpleOrdersByStock, d -> d.getItems().stream().collect(Collectors.groupingBy(SimpleItem::getDispatchWay)));


    Assert.assertTrue(simpleOrdersByStock.size() == 2);
    Assert.assertTrue(simpleOrderByDispatchWay.size() == 2);
  }

  @Before
  public void init() {
    this.simpleOrder = new SimpleOrder();
    simpleOrder.setOrderId(UUID.randomUUID().toString());

    SimpleItem item01 = new SimpleItem();
    item01.setSkuId("1").setDispatchWay(1).setStock("s0001");
    SimpleItem item02 = new SimpleItem();
    item02.setSkuId("2").setDispatchWay(1).setStock("s0002");
    SimpleItem item03 = new SimpleItem();
    item03.setSkuId("3").setDispatchWay(2).setStock("s0002");
    SimpleItem item04 = new SimpleItem();
    item04.setSkuId("4").setDispatchWay(2).setStock("s0002");

    Arrays.asList(item01, item02, item03, item04)
        .stream()
        .forEach(simpleOrder::addSimpleItem);
  }

  private SimpleOrder simpleOrder;


}
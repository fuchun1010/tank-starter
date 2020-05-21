package com.tank.goods;

import lombok.Getter;
import lombok.val;
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

  @Test
  public void testDir() {

    val keys = System.getProperties().keys();
    while (keys.hasMoreElements()) {
      String key = keys.nextElement().toString();
      System.out.println(String.format("%s, value:[%s]", key, System.getProperty(key)));
    }
  }

  @Test
  public void testSplit() {
    val result = "[\"https://cdn.pixabay.com/photo/2019/01/25/11/18/girl-3954232__340.jpg\",\"https://cdn.pixabay.com/photo/2018/11/11/16/51/ibis-3809147__340.jpg\",\"https://cdn.pixabay.com/\n" +
            "photo/2018/07/16/13/17/kiss-3541905__340.jpg\"]";

    int startIndex = result.indexOf("[") + 1;
    int endIndex = result.indexOf("]") - 1;
    System.out.println(result.substring(startIndex, endIndex));

  }




  private static class CalculatorTask implements Runnable {

    public CalculatorTask(int value) {
      this.value = value;
    }

    @Getter
    private int value;

    @Override
    public void run() {
      this.value++;
    }


  }

  private SimpleOrder simpleOrder;


}
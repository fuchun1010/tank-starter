package com.tank.spike;

import com.annimon.stream.Stream;
import com.tank.invoice.InvoiceReq;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class InvoiceTest {

  @Test
  public void doneCheck() {
    InvoiceReq invoiceReq = new InvoiceReq();
    invoiceReq.setOrderNo("s001").setBuyerName("xx");
    Optional<String> resultOpt = invoiceReq.mustFieldsIsOk();
    System.out.println(resultOpt.get());
  }


  @Test
  public void reduceSet() {
    String[] arr = new String[]{"a", "b", "c"};
    Set<String> collections = Stream.of(arr).reduce(new HashSet<>(), (a, b) -> {
      a.add(b);
      return a;
    });
    for (String str : collections) {
      System.out.println(str);
    }
  }

  @Test
  public void reduceSet2() {
    String[] arr = new String[]{"a", "b", "c"};
    Set<String> results = Arrays
        .stream(arr)
        .reduce(new HashSet<>(), (a, b) -> {
          a.add(b);
          return a;
        }, (c, d) -> {
          c.addAll(d);
          return c;
        });
    for (String result : results) {
      System.out.println(result);
    }
  }

}

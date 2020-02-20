package com.tank.spike;

import com.annimon.stream.Stream;
import com.google.common.base.MoreObjects;
import lombok.*;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class CollectionSpikeTest {

  @Test
  public void testAdd() {
    Person lisi = new Person("lisi", "bj");
    Person wangwu = new Person("wangwu", "Cq");
    val result = Stream.of(lisi, wangwu).groupBy(Person::getName).toList();
    for (Map.Entry<String, List<Person>> entry : result) {

    }


  }

  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  private static class Person {

    @Override
    public String toString() {
      return MoreObjects
          .toStringHelper(this)
          .add("username", this.name)
          .add("userAddress", this.address)
          .toString();
    }

    private String name;

    private String address;
  }

}

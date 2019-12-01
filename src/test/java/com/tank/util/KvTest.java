package com.tank.util;

import com.tank.util.basic.KvConverter;
import com.tank.util.json.Person;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class KvTest {

  @Test
  public void testToKv() {
    Person person = new Person();
    person.setName("json");
    Map<String, String> value = this.kvConverter.toKv(person);
    Assert.assertTrue(value.size() == 1);
  }

  @Test
  public void testToObject() {
    Map<String, String> data = new HashMap<>(initialCapacity);
    data.putIfAbsent("name", "wangwu");
    Person person = this.kvConverter.toObj(data, Person.class);
    Assert.assertNotNull(person);
    Assert.assertTrue("wangwu".equalsIgnoreCase(person.getName()));
  }


  @Before
  public void init() {
    this.kvConverter = new KvConverter();
  }


  private KvConverter kvConverter;

  private int initialCapacity = 2 << 3;

}

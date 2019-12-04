package com.tank.util;

import com.tank.util.basic.Color;
import com.tank.util.basic.KvConverter;
import com.tank.util.json.Person;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class IocTest {

  @Test
  public void testConverter() {
    Person person = new Person();
    person.setName("lisi");
    person.setGender("male");
    Assert.assertNotNull(this.kvConverter);
    Map<String, String> rs = this.kvConverter.toKv(person);
    Assert.assertTrue(rs.size() == 2);
  }

  @Test
  public void testColor() {
    Assert.assertNotNull(this.color);
  }

  @Autowired
  private KvConverter kvConverter;

  @Autowired
  private Color color;
}

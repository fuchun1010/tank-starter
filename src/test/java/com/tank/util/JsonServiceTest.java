package com.tank.util;

import com.tank.util.basic.EnableKvConverter;
import com.tank.util.json.JsonService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class JsonServiceTest {

  @Test
  public void testJsonServiceExists() {
    Assert.assertNotNull(this.annotationContext);
    Assert.assertTrue(this.annotationContext.containsBean("jsonService"));
    for (String name : this.annotationContext.getBeanDefinitionNames()) {
      System.out.println(name);
    }
  }

  @Before
  public void init() {
    this.annotationContext = new AnnotationConfigApplicationContext();
    this.annotationContext.register(JsonService.class);
    this.annotationContext.register(EnableKvConverter.class);
  }

  @Test
  public void testGetterToFieldName() {
    String getter = "getName";
    String tail = getter.substring(4, getter.length());
    String header = getter.substring(3, 4).toLowerCase();
    String fieldName = String.format("%s%s", header, tail);
    System.out.println(fieldName);
  }


  @After
  public void end() {
    if (this.annotationContext != null) {
      this.annotationContext.close();
      System.out.printf("execute close context");
    }
  }


  private AnnotationConfigApplicationContext annotationContext;


}

package com.tank.util;

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

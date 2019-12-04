package com.tank.util;

import lombok.val;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author tank198435163.com
 */
@SpringBootApplication
public class Main {

  public static void main(String[] args) {
    val context = SpringApplication.run(Main.class, args);
    for (String name : context.getBeanDefinitionNames()) {
      System.out.println(name);
    }
  }
}

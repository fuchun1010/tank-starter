package com.tank.util;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author tank198435163.com
 */
//@SpringBootApplication
public class App {
  public static void main(String[] args) {


    ConfigurableApplicationContext context = SpringApplication.run(App.class, args);
    System.out.println(context.containsBean("person"));
    System.out.println(context.containsBean("jsonService"));

//    for (String definitionName : context.getBeanDefinitionNames()) {
//      if (definitionName.indexOf("org") != -1) {
//        continue;
//      }
//      System.out.println(definitionName);
//    }

//    System.out.println(context.containsBean("jsonService"));
//    JsonService jsonService = context.getBean("jsonService", JsonService.class);
//    System.out.println(jsonService);
  }
}

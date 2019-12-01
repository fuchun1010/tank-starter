package com.tank.util.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tank.util.basic.EnableKvConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author tank198435163.com
 */
@Configuration
@EnableKvConverter
public class JsonServiceConfigure {

  @Bean("jsonService")
  @ConditionalOnClass(ObjectMapper.class)
  public JsonService createJsonService() {
    ObjectMapper objectMapper = new ObjectMapper();
    JsonService jsonService = new JsonService(objectMapper);
    return jsonService;
  }

  @Bean("person")
  public Person createPerson() {
    Person person = new Person();
    person.setName("lisi");
    return person;
  }
}

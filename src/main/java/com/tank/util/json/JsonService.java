package com.tank.util.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.springframework.util.Assert;

/**
 * @author tank198435163.com
 */
@AllArgsConstructor
public class JsonService {

  @SneakyThrows
  public <T> String convert2Str(@NonNull final T data) {
    Assert.state(this.objectMapper != null, "json ObjectMapper not inject");
    return this.objectMapper.writeValueAsString(data);
  }

  private ObjectMapper objectMapper;

}

package com.tank.util.redis;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author tank198435163.com
 */
@AllArgsConstructor
public class RedisService {

  public Optional<String> fetchValueWithString(@NonNull final String k) {
    Object rs = this.redisTemplate.opsForValue().get(k);
    return rs == null ? Optional.empty() : Optional.of(rs.toString());

  }

  public void assignKvWithString(String k, Object v) {
    this.assignKvWithString(k, v, TimeUnit.MINUTES, 15L);
  }

  public void assignKvWithString(String k, Object v, TimeUnit timeUnit, Long value) {
    this.redisTemplate.opsForValue().set(k, v);
    this.redisTemplate.expire(k, value, timeUnit);
  }

  private RedisTemplate<String, Object> redisTemplate;

}

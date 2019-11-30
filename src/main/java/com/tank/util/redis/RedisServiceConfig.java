package com.tank.util.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author tank198435163.com
 */
@Configuration
public class RedisServiceConfig {

  @Bean("redisService")
  @ConditionalOnClass(RedisTemplate.class)
  @ConditionalOnBean(RedisTemplate.class)
  public RedisService createRedisService(@Autowired RedisTemplate<String, Object> redisTemplate) {
    return new RedisService(redisTemplate);
  }

  @Bean("redisTemplate")
  @ConditionalOnClass(ObjectMapper.class)
  @ConditionalOnProperty(prefix = "spring.redis", name = {"database"})
  public RedisTemplate<String, Object> createRedisTemplate(@Autowired RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(redisConnectionFactory);
    Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
    objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
    jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
    redisTemplate.afterPropertiesSet();
    return redisTemplate;
  }


}

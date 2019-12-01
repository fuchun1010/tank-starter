package com.tank.util.basic;

import lombok.NonNull;
import lombok.SneakyThrows;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author tank198435163.com
 */
public class KvConverter {

  @SneakyThrows
  public <T> Map<String, String> toKv(@NonNull final T obj) {

    Map<String, String> maps = new HashMap(this.capacity);

    Method[] allMethods = obj.getClass().getDeclaredMethods();

    List<Method> allGetter = Arrays.stream(allMethods)
        .filter(method -> method.getName().startsWith("get"))
        .filter(method -> method.getName().length() > "get".length())
        .collect(Collectors.toList());

    for (Method method : allGetter) {
      Object result = method.invoke(obj, new Object[]{});
      boolean isNotOk = result == null || !this.onlyIncludeBasicType(result.getClass());
      if (isNotOk) {
        continue;
      }

      maps.put(this.convertGetterToFieldName(method.getName()), String.valueOf(result));
    }

    return maps;
  }

  @SneakyThrows
  public <T> T toObj(@NonNull final Map<String, String> data, @NonNull final Class<T> clazz) {

    if (data.isEmpty()) {
      throw new IllegalArgumentException("data not allowed empty");
    }

    T obj = clazz.newInstance();
    List<Method> methods = Arrays.stream(obj.getClass().getDeclaredMethods())
        .filter(method -> method.getName().startsWith("set"))
        .filter(method -> method.getName().length() > "set".length())
        .collect(Collectors.toList());

    if (methods.size() == 0) {
      throw new Exception(String.format("there's not setter method in :[%s]", clazz.getName()));
    }

    Map<String, Method> setters = new HashMap<>(capacity);
    for (Method method : methods) {
      String fieldName = this.convertGetterToFieldName(method.getName());
      setters.putIfAbsent(fieldName, method);
    }

    for (Map.Entry<String, Method> methodEntry : setters.entrySet()) {
      String key = methodEntry.getKey();
      Method method = methodEntry.getValue();
      String value = data.get(key);
      if (StringUtils.isEmpty(value)) {
        continue;
      }
      method.invoke(obj, new Object[]{value});
    }

    return obj;
  }


  private String convertGetterToFieldName(@NonNull final String getterOrSetter) {
    int len = getterOrSetter.length();
    String tail = getterOrSetter.substring(4, len);
    String header = getterOrSetter.substring(3, 4).toLowerCase();
    return String.format("%s%s", header, tail);
  }

  private boolean onlyIncludeBasicType(Class<?> clazz) {
    for (Class<?> clazzType : clazzTypes) {
      if (clazzType == clazz) {
        return true;
      }
    }
    return false;
  }


  private Class<?>[] clazzTypes = new Class[]{
      byte.class,
      Byte.class,
      short.class,
      Short.class,
      int.class,
      Integer.class,
      long.class,
      Long.class,
      boolean.class,
      Boolean.class,
      String.class
  };

  private int capacity = 1 << 7;
}

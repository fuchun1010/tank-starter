package com.tank.util.api;

import lombok.NonNull;

import java.lang.reflect.Proxy;

public class ApiProxyHandler {

  public <I> Object done(@NonNull final I obj) {
    ClassLoader classLoader = obj.getClass().getClassLoader();
    return Proxy.newProxyInstance(classLoader, obj.getClass().getInterfaces(), new ApiProxy<I>(obj));
  }
}

package com.tank.util.api;

import lombok.NonNull;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Objects;

public class ApiProxy<I> implements InvocationHandler {

  public ApiProxy(@NonNull final I api) {
    this.api = api;
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    Class<?>[] interfaces = this.api.getClass().getInterfaces();
    if (Objects.isNull(interfaces) || interfaces.length == 0) {
      throw new IllegalArgumentException("proxy must implements Interface");
    }
    System.out.println("start");
    Object rs = method.invoke(this.api, args);
    System.out.println("end");
    return rs;
  }

  private I api;


}

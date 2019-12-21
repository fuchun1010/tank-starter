package com.tank.util.api;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Date;

public class LogInterceptor implements MethodInterceptor {

  @Override
  public Object intercept(Object object, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
    this.before();
    Object rs = methodProxy.invokeSuper(object, objects);
    this.after();
    return rs;
  }

  private void before() {
    System.out.println(String.format("log start time [%s] ", new Date()));
  }

  private void after() {
    System.out.println(String.format("log end time [%s] ", new Date()));
  }


}

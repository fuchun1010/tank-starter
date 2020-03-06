package com.tank.jproxy;

import java.lang.reflect.Proxy;

public class LocalTransactionProxy {


  public <T> T addTransactionProxy(Object target, Class<?> implementsClazz) {
    Class<?> clazz = target.getClass();
    T targetWithProxy = (T) Proxy.newProxyInstance(
        clazz.getClassLoader(),
        clazz.getInterfaces(),
        new GlobalLocalTransaction(clazz, implementsClazz));
    return targetWithProxy;
  }

}

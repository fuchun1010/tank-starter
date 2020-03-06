package com.tank.jproxy;

import lombok.SneakyThrows;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

public class GlobalLocalTransaction implements InvocationHandler {

  public GlobalLocalTransaction(Object object, Class<?> implementsClazz) {
    this.target = checkNotNull(object);
    this.implementsClazz = implementsClazz;
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

    Connection conn = this.createConn();
    conn.setAutoCommit(false);
    Method connMethod = this.searchDbConnMethod(implementsClazz);
    if (connMethod == null) {
      throw new IllegalArgumentException(String.format("class:[{}] 不存在传递conn的方法", target.getClass().getSimpleName()));
    }
    if (!connMethod.isAccessible()) {
      connMethod.setAccessible(true);
    }
    Object tmp = implementsClazz.newInstance();
    connMethod.invoke(tmp, conn);
    Object result = null;
    try {
      result = method.invoke(tmp, args);
      conn.commit();
      System.out.println("commit");
      return result;
    } catch (Exception e) {
      conn.rollback();
      e.printStackTrace();
    } finally {
      if (conn != null) {
        System.out.println("close db connection");
        conn.close();
      }
    }

    return result;
  }

  @SneakyThrows
  private Connection createConn() {
    Class.forName("com.mysql.cj.jdbc.Driver");
    Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:4000/students", "root", "");
    return connection;
  }

  private Method searchDbConnMethod(final Class<?> clazz) {
    Method[] methods = clazz.getDeclaredMethods();
    for (Method method : methods) {
      Parameter[] parameters = method.getParameters();
      boolean isEmpty = Objects.isNull(parameters) || parameters.length != 1;
      if (isEmpty) {
        continue;
      }
      String parameterClassName = parameters[0].getParameterizedType().getTypeName();
      boolean isJdbcConn = Connection.class.getName().equalsIgnoreCase(parameterClassName);
      if (isJdbcConn) {
        return method;
      }
    }

    return null;
  }


  private Object target;

  private Class<?> implementsClazz;

}

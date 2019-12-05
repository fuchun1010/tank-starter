package com.tank.util.rdb;

import javafx.util.Pair;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Properties;
import java.util.function.Function;

/**
 * @author tank198435163.com
 */
public class MySqlDb<T> implements Rdb {

  public MySqlDb(@NonNull final Collection<T> collections) {
    this.collections = collections;
  }

  @Override
  public Properties createProps(Pair<String, String>... pairs) {
    Properties properties = new Properties();
    for (Pair<String, String> pair : pairs) {
      properties.put(pair.getKey(), pair.getValue());
    }
    return properties;
  }

  public Collection<T> toCollection(@NonNull final Properties properties,
                                    @NonNull final String sql,
                                    Function<ResultSet, T> fun) {

    Collection<T> collections = new LinkedList<>();
    try (Connection conn = this.createConn(properties)) {
      Statement smt = conn.createStatement();
      ResultSet rs = smt.executeQuery(sql);
      while (rs.next()) {
        T data = fun.apply(rs);
        collections.add(data);
      }
      smt.close();
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return collections;
  }



  @SneakyThrows
  private Connection createConn(Properties properties) {
    boolean isNotOk = properties.keySet().size() != 3;
    if (isNotOk) {
      throw new IllegalArgumentException("请检查用户名,密码,url链接地址是否传送");
    }
    Class.forName("com.mysql.cj.jdbc.Driver");
    String username = properties.getProperty("username", "root");
    String password = properties.getProperty("passowrd", "123");
    String url = properties.getProperty("url", "jdbc:mysql://localhost:3307/order_crm");
    return DriverManager.getConnection(url, username, password);
  }

  private Collection<T> collections;


}

package com.tank.util.rdb;

import javafx.util.Pair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class MySqlDbTest {

  @Test
  public void toTables() {
    Collection<String> tables = this.fetchTables();
    Assert.assertTrue(tables.size() > 0);
  }


  @Test
  public void toSqlBody() {
    Collection<String> tables = this.fetchTables();
    List<String> result = new LinkedList<>();
    for (String table : tables) {
      String sql = String.format("show create table %s", table);
      Collection<String> sqlStatement = this.mySqlDb.toCollection(this.props, sql, rs -> {
        try {
          return rs.getString(2);
        } catch (SQLException e) {
          e.printStackTrace();
        }
        return "";
      });
      String sqlBody = sqlStatement.stream().findFirst().get();
      //System.out.println(sqlBody);
      for (int i = 0; i < 2; i++) {
        String newTable = sqlBody.replace(table, String.format("%s_%d", table, i));
        result.add(newTable);
        result.add("\n");
      }
    }

    result.forEach(System.out::println);
  }


  @Before
  public void init() {
    this.mySqlDb = new MySqlDb<>(new HashSet<>());
    this.props = this.mySqlDb.createProps(
        new Pair<>("url", "jdbc:mysql://localhost:3307/order_crm"),
        new Pair<>("username", "root"),
        new Pair<>("password", "123")
    );
  }

  private Collection<String> fetchTables() {
    Collection<String> collections = this.mySqlDb.toCollection(props, "show tables", rs -> {
      try {
        return rs.getString(1);
      } catch (SQLException e) {
        e.printStackTrace();
      }
      return "";
    });

    List<String> tables = collections.stream().filter(item -> item.trim().length() > 0).collect(Collectors.toList());
    return tables;
  }

  private MySqlDb<String> mySqlDb;

  private Properties props;
}
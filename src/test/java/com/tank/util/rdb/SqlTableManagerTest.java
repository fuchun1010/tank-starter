package com.tank.util.rdb;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;

import static org.junit.Assert.*;

public class SqlTableManagerTest {

  @Test
  public void createSqlTableScript() {
    Properties props = new Properties();
    props.put("username", "root");
    props.put("password", "123");
    props.put("url", "jdbc:mysql://localhost:3307/order_crm");
    Map<String, Object> data = this.sqlTableManager.createSqlTableScript(props, 2);
    Assert.assertTrue(data.size() > 0);
    this.sqlTableManager.generateSql(data);
  }

  @Before
  public void init() {
    this.sqlTableManager = new SqlTableManager(new MySqlDb<String>(new ArrayList<>()));
  }

  private SqlTableManager sqlTableManager;
}
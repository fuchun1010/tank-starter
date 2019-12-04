package com.tank.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.Cleanup;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.sql.*;
import java.util.*;
import java.util.function.Function;

public class FreeMarkerTest {


  @Test
  public void testGenerateTables() {
    List<SqlBody> result = this.doneWithMysql(conn -> {
      String tables = "show tables";
      List<SqlBody> allTables = new LinkedList<>();
      try {
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(tables);
        rs.setFetchDirection(ResultSet.FETCH_FORWARD);
        SqlBody tableContent;
        while (rs.next()) {
          String tableName = rs.getString(1);
          String sql = String.format("show create table %s", tableName);
          ResultSet tableCursor = statement.executeQuery(sql);
          tableCursor.setFetchDirection(ResultSet.FETCH_FORWARD);
          while (tableCursor.next()) {
            String sqlBody = rs.getString(2);
            for (int i = 0; i < 4; i++) {
              tableContent = new SqlBody();

              String newSqlBody = sqlBody.replace(tableName, String.format("%s_%d", tableName, i));
              newSqlBody = String.format("%s;", newSqlBody);
              tableContent.setTable(tableName);
              tableContent.setSqlBody(newSqlBody.trim());
              allTables.add(tableContent);
            }
          }
          tableCursor.close();
        }
        rs.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
      return allTables;
    });


  }


  @Test
  @SneakyThrows
  public void generateSql() {
    String download = String.format("%s/download", System.getProperty("user.dir"));
    String path = String.format("%s/template", System.getProperty("user.dir"));
    Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
    cfg.setDefaultEncoding("UTF-8");
    cfg.setDirectoryForTemplateLoading(new File(path));
    Template template = cfg.getTemplate("sql-tables.ftl");
    List<SqlBody> sqlBodies = new ArrayList<>();
    @Cleanup Writer out = new FileWriter(new File(String.format("%s/table.sql", download)));
    Map<String, List<SqlBody>> data = new HashMap<>();
    data.putIfAbsent("data", sqlBodies);
    // Map<String, String> data = new HashMap<>();
    //data.putIfAbsent("hello", "xx");
    template.process(data, out);


  }

  @SneakyThrows
  private Connection createConn() {
    Class.forName("com.mysql.cj.jdbc.Driver");
    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/order_crm", "root", "123");
    return conn;
  }

  @Getter
  @Setter
  public static class SqlBody {

    private String table;
    private String sqlBody;
  }

  public List<SqlBody> doneWithMysql(Function<Connection, List<SqlBody>> fun) {

    try (Connection conn = this.createConn()) {
      return fun.apply(conn);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;

  }

}

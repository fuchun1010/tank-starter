package com.tank.util.rdb;

import freemarker.template.Configuration;
import freemarker.template.Template;
import javafx.util.Pair;
import lombok.Cleanup;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class SqlTableManager implements SqlTableScript<String> {

  public SqlTableManager(Rdb rdb) {
    this.rdb = rdb;
  }

  @Override
  @SneakyThrows
  public Map<String, Object> createSqlTableScript(@NonNull final String dbName, @NonNull Properties props, @NonNull Integer splitNumberPerTable) {

    if (!this.isPowerOf2(splitNumberPerTable)) {
      throw new IllegalArgumentException("splitNumberPerTable必须是2的n次方");
    }

    Properties connInfo = this.rdb.createProps(
        new Pair<>("username", props.get("username").toString()),
        new Pair<>("url", props.get("url").toString()),
        new Pair<>("password", props.get("password").toString()));

    MySqlDb<String> mySqlDb = new MySqlDb<>(new LinkedList<>());
    Collection<String> tables = mySqlDb.toCollection(connInfo, "show tables", rs -> {
      try {
        return rs.getString(1).trim();
      } catch (SQLException e) {
        e.printStackTrace();
      }
      return "";
    }).stream()
        .filter(t -> t.trim().length() > 1)
        .collect(Collectors.toList());

    mySqlDb = new MySqlDb<>(new LinkedList<>());

    List<String> result = new LinkedList<>();
    for (String table : tables) {
      String sql = String.format("show create table %s", table);
      Collection<String> sqlStatement = mySqlDb.toCollection(connInfo, sql, rs -> {
        try {
          return rs.getString(2).trim();
        } catch (SQLException e) {
          e.printStackTrace();
        }
        return "";
      });
      String sqlBody = sqlStatement.stream().findFirst().get();
      //System.out.println(sqlBody);
      for (int i = 0; i < splitNumberPerTable; i++) {
        String newTable = sqlBody.replace(table, String.format("%s_%d", table, i));
        result.add(newTable);
      }
    }

    Map<String, Object> map = new HashMap<>(1 << 4);
    map.put("data", result);
    map.putIfAbsent("dbName", dbName);
    return map;
  }

  @SneakyThrows
  public void generateSql(@NonNull final Map<String, Object> data, String sqlScriptName) {
    if (data.size() == 0) {
      throw new IllegalArgumentException("");
    }
    String download = String.format("%s/download", System.getProperty("user.dir"));
    String path = String.format("%s/template", System.getProperty("user.dir"));
    Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
    cfg.setDefaultEncoding("UTF-8");
    cfg.setDirectoryForTemplateLoading(new File(path));
    Template template = cfg.getTemplate("sql-tables.ftl");

    @Cleanup Writer out = new FileWriter(new File(String.format("%s/%s.sql", download, sqlScriptName)));
    template.process(data, out);
  }


  private boolean isPowerOf2(int number) {
    return (number & (number - 1) - 1) == 0;
  }


  private Rdb rdb;
}

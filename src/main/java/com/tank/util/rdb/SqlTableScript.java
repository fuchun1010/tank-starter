package com.tank.util.rdb;

import lombok.NonNull;

import java.util.Map;
import java.util.Properties;

public interface SqlTableScript<T> {


  Map<String, Object> createSqlTableScript(@NonNull final String dbName, @NonNull final Properties props, @NonNull Integer splitNumberPerTable);


}

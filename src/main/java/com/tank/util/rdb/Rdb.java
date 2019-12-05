package com.tank.util.rdb;

import javafx.util.Pair;

import java.util.Properties;

/**
 * @author tank198435163.com
 */
public interface Rdb {

  Properties createProps(Pair<String, String>... pairs);
}

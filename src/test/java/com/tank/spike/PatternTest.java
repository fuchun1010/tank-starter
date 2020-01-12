package com.tank.spike;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternTest {

  @Test
  public void testExit() {
    boolean isMatched = this.exitPattern.matcher("exit").matches();
    Assert.assertTrue(isMatched);
  }

  @Test
  public void testPeriod() {
    String DEFAULT_PERIOD = "1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h";
    String pattern = "\\s+";
    for (String s : DEFAULT_PERIOD.split(pattern)) {
      System.out.println(s);
    }
  }


  @Test
  public void testDelimiter() {
    Pattern delimiter = Pattern.compile("([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}");
    Matcher matcher = delimiter.matcher("182.192.110.121");
    while (matcher.find()) {
      System.out.println(matcher.group());
    }

  }

  @Before
  public void init() {
    this.exitPattern = Pattern.compile("exit$");
  }

  private Pattern exitPattern;
}

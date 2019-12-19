package com.tank.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.regex.Pattern;

public class RegularTest {

  @Test
  public void testDate() {
    boolean isMatched = this.datePattern.matcher("2019-12-11").matches();
    Assert.assertTrue(isMatched);
  }

  @Test
  public void testDate2() {
    boolean isMatched = this.datePattern.matcher("2019:12-11").matches();
    Assert.assertFalse(isMatched);
  }

  @Test
  public void testDate3() {
    boolean isMatched = this.timePattern.matcher("11:12:21").matches();
    Assert.assertTrue(isMatched);
  }

  @Test
  public void testDate4() {
    boolean isMatched = this.timePattern.matcher("11:xx:21").matches();
    Assert.assertFalse(isMatched);
  }

  @Before
  public void init() {
    this.datePattern = Pattern.compile("(\\d{4})-(\\d+)-(\\d+).*");
    this.timePattern = Pattern.compile("(\\d{2}):(\\d{2}):(\\d{2}).*");
  }

  Pattern datePattern = null;
  Pattern timePattern = null;
}

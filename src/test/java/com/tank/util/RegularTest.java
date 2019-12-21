package com.tank.util;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import lombok.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
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

  @Test
  public void testExtDir() {
    String rs = System.getProperty("java.ext.dirs");
    System.out.println(rs);
    StringTokenizer stringTokenizer = new StringTokenizer(rs, File.pathSeparator);

    int count = stringTokenizer.countTokens();
    System.out.println(count);
  }

  @Test
  @SneakyThrows
  public void testClassLoader() {
    String fullName = "com.rain.hello.Hello";
    String fullPath = fullName.replace(".", File.separator);
    String path = String.format("%s%s", "/Users/tank198435163.com/javadone/", fullPath);
    DiskClassLoader diskClassLoader = new DiskClassLoader(path);

    Class<?> clazz = diskClassLoader.loadClass(fullName);
    Object obj = clazz.newInstance();
    Method method = obj.getClass().getDeclaredMethod("sayHello", new Class[]{});
    if (method != null) {
      Object rs = method.invoke(obj, new Object[]{});
      System.out.println(rs);
    }
  }

  @Test
  public void testItems() {
    Map<String, List<Item>> map = Stream.of(
        new Item(15, "s0001"),
        new Item(12, "s0001"))
        .collect(Collectors.groupingBy(Item::getOrderNo));

    for (Map.Entry<String, List<Item>> entry : map.entrySet()) {
      Integer rs = Stream.of(entry.getValue()).map(Item::getPrice).reduce(0, Integer::sum);
      System.out.println(entry.getKey() + ":" + rs);
    }

  }


  private static class DiskClassLoader extends ClassLoader {


    public DiskClassLoader(String dirPath) {
      this.dirPath = dirPath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
      byte[] classBinary = this.loadClassBy(name);
      return super.defineClass(name, classBinary, 0, classBinary.length);
    }

    @SneakyThrows
    private byte[] loadClassBy(String name) {
      String classFilePath = String.format("%s.class", this.dirPath);
      File file = new File(classFilePath);
      if (!file.exists()) {
        throw new FileNotFoundException(String.format("%s not found", classFilePath));
      }
      @Cleanup ByteArrayOutputStream out = new ByteArrayOutputStream();

      @Cleanup FileInputStream in = new FileInputStream(file);

      while (true) {
        int c = in.read();
        if (c == -1) {
          break;
        }
        out.write(c);
        out.flush();
      }

      return out.toByteArray();
    }

    private String dirPath;

    private String classLoaderName;
  }


  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  private static class Item {


    private Integer price;

    private String orderNo;
  }

  @Before
  public void init() {
    this.datePattern = Pattern.compile("(\\d{4})-(\\d+)-(\\d+).*");
    this.timePattern = Pattern.compile("(\\d{2}):(\\d{2}):(\\d{2}).*");
  }

  Pattern datePattern = null;
  Pattern timePattern = null;
}

package com.tank.util;

import lombok.SneakyThrows;
import lombok.val;
import org.junit.Test;

import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.ConcurrentSkipListSet;

public class SkipTableTest {

  @Test
  public void testOpOnSkipTable() {
    val skip = new ConcurrentSkipListSet<String>();
    skip.add("hello");
    skip.add("skip");
    skip.add("table");
    Iterator<String> iterator = skip.iterator();
    while (iterator.hasNext()) {
      String item = iterator.next();
      System.out.println(item);
    }
    Integer[] arr = new Integer[]{1, 9, 2, 10, 7};
    Arrays.sort(arr, Integer::compareTo);

  }

  @Test
  @SneakyThrows
  public void testCopy() {


    FileChannel in = FileChannel.open(Paths.get(this.getClass().getClassLoader().getResource("1.txt").toURI()), StandardOpenOption.READ);
    FileChannel out = FileChannel.open(Paths.get("2.txt"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE_NEW);

    MappedByteBuffer inBuffer = in.map(FileChannel.MapMode.READ_ONLY, 0, in.size());
    MappedByteBuffer outBuffer = out.map(FileChannel.MapMode.READ_WRITE, 0, in.size());

    byte[] dst = new byte[inBuffer.limit()];
    inBuffer.get(dst);
    outBuffer.put(dst);

    inBuffer.clear();
    outBuffer.clear();

    in.close();
    out.close();


  }


  private int M = 1024;
}

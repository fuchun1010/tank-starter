package com.tank.util.ds;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.HashMultiset;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;

public class QueueTest {

  @Test
  @SneakyThrows
  public void testPop() {
    while (true) {
      String rs = this.queue.take();
      System.out.println(rs);
    }
  }

  @Test
  public void testDelimiter() {
    String delimiter = Character.toString((char) FIELD_DELIMITER);
    System.out.println(delimiter);
    System.out.println(0x3F);
  }

  @Test
  public void testMultiSet() {
    HashMultiset<String> multiset = HashMultiset.create();
    multiset.add("hello");
    multiset.add("hello");
    multiset.add("world");
    int n = multiset.count("hello");
    System.out.println(n);
    multiset.remove("hello", 10);
    System.out.println(multiset.count("hello"));

    HashMultimap<String, String> multimap = HashMultimap.create();
    multimap.get("xx").add("hello");
    System.out.println(multimap.get("xx").size());

  }


  @Test
  public void testEnum() {
    Message.Type type = Message.Type.valueOf("DISCONNECT");

    switch (type) {
      case DISCONNECT:
        System.out.println("disconnect");
        break;
      default:
        System.out.println("mao xian");
    }
  }


  private static class Message {

    public enum Type {
      DISCONNECT, USER_TEXT, SERVER_TEXT
    }
  }

  @Before
  public void init() {
    this.queue.add("hello");
  }

  private ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(100);

  public static final byte FIELD_DELIMITER = 0x1F;

}

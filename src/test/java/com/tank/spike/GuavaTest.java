package com.tank.spike;

import com.annimon.stream.IntStream;
import com.annimon.stream.Stream;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.collect.*;
import com.tank.splitter.Order;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.google.common.base.Joiner.on;
import static java.lang.String.format;

public class GuavaTest {

  @Test
  @SneakyThrows
  public void testJoiner() {
    String result = on(separator).skipNulls().join(this.jobs);
    @Cleanup val out = on(separator)
        .appendTo(new OutputStreamWriter(new FileOutputStream(
            new File("joiner.txt"))), this.jobs);
    out.flush();
    System.out.println(result);
  }

  @Test
  public void testSplitter() {
    String inputStr = on(this.separator).skipNulls().join(this.jobs);
    List<String> splitedResult = Splitter.on(separator).splitToList(inputStr);
    Iterator<String> iterator = Splitter.on(separator).trimResults().split(inputStr).iterator();
    while (iterator.hasNext()) {
      String str = iterator.next();
      System.out.println(str);
    }
  }

  @Test
  public void testPreConditions() {
    String[] data = new String[this.jobs.size()];
    this.jobs.toArray(data);
    Preconditions.checkElementIndex(10, data.length, "over max index of array");
  }

  @Test
  public void testBase64Encoder() {
    byte[] result = Base64.getEncoder().encode("hello".getBytes());
    Preconditions.checkArgument(result.length > 1);
    System.out.println(new String(result));
    System.out.println(new String(Base64.getDecoder().decode(result)));
  }

  @Test
  public void testBitMap() {
    LinkedListMultimap<String, String> maps = LinkedListMultimap.create();
    maps.put("1", "1");
    maps.put("1", "2");

    System.out.println(maps.get("1"));
    HashBiMap<String, String> maps1 = HashBiMap.create();

    maps1.put("1", "hello");
    System.out.println(maps1.inverse().get("hello"));

    Table<String, String, Integer> table = HashBasedTable.create();

    table.put("s001", "job", 1);
    table.put("s001", "age", 12);


    for (String rowKey : table.rowKeySet()) {
      Map<String, Integer> columns = table.row(rowKey);
      for (Map.Entry<String, Integer> entry : columns.entrySet()) {
        System.out.println(format("column name:%s, value :%d", entry.getKey(), entry.getValue()));
      }
    }

    Range<Integer> range = Range.closed(0, 10);

    System.out.println(range.contains(0));
    System.out.println(range.contains(10));


    ImmutableList.<Integer>builder().add(1).add(2).build();
  }


  @Test
  public void testCache() {
    CacheLoader<String, Order> cacheLoader = CacheLoader.from(k -> Objects.isNull(k) ? null : new Order());
    LoadingCache<String, Order> loadingCache = CacheBuilder.newBuilder().build(cacheLoader);
    loadingCache.put("order1", new Order());
    Order target = loadingCache.getUnchecked("order1");
    System.out.println(target);
    RemovalListener<String, String> listener = notification -> {

    };

    Map<String, String> d = new HashMap() {
      {
        put("x", "x");
      }
    };
  }


  @Test
  public void testNum() {
    int inputData = 1;
    int total = new Double(Math.log10(1000)).intValue();
    int current = new Double(Math.log10(inputData)).intValue();
    int differ = total - current - 1;
    String prefix = Joiner.on("").join(IntStream.range(0, differ).boxed().map(i -> "0").toList());
    String result = String.format("#%s%d", prefix, inputData);
    System.out.println(result);
    System.out.println(1 << 2);
  }

  @Test
  public void testCache2() {
    LoadingCache<String, String> cacheBuilder = CacheBuilder
        .newBuilder()
        .maximumSize(10)
        .build(new CacheLoader<String, String>() {
          @Override
          public String load(String key) throws Exception {
            return "hello";
          }
        });

    cacheBuilder.put("x", "xx");

    System.out.println(cacheBuilder.getUnchecked("pp"));
    System.out.println(cacheBuilder.getUnchecked("pp"));
  }

  @Test
  public void testRange() {

    Map<Range<Integer>, String> meta = Maps.newHashMap();

    Range<Integer> r1 = Range.closed(0, 9);
    Range<Integer> r2 = Range.closed(10, 19);
    Range<Integer> r3 = Range.closed(20, 29);

    meta.putIfAbsent(r1, "tab_1");
    meta.putIfAbsent(r2, "tab_2");
    meta.putIfAbsent(r3, "tab_3");


    List<Integer> mostValues = Stream.of(meta.entrySet()).map(entry -> entry.getKey().upperEndpoint()).toList();
    Integer[] data = new Integer[mostValues.size()];
    mostValues.toArray(data);
    Arrays.sort(data);
    System.out.println(data);
  }

  @Test
  public void testDateTime() {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
    LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()), ZoneId.systemDefault());
    String result = dateTimeFormatter.format(localDateTime);
    System.out.println(result);
  }

  @Test
  public void testTimeStamp() {
    //2147483647
    long first = 9223372036854775807L;
    long second = System.currentTimeMillis();
    System.out.println(second);
    System.out.println(Long.compare(second, first));
  }

  @Test
  public void testPk() {

    // -1
    //正码
    //1000 0000 0000 0000 0000 0000 0000 0001
    //反码
    //1111 1111 1111 1111 1111 1111 1111 1110
    //补码
    //1111 1111 1111 1111 1111 1111 1111 1111

    System.out.println(Integer.toBinaryString(-1));

    LocalDateTime dt = LocalDateTime.now();
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
    String prefix = dateTimeFormatter.format(dt);
    System.out.println(prefix);
    String result = format("%s%s", prefix, "0000001");
    System.out.println(StringUtils.reverse(result));
    System.out.println(Long.MAX_VALUE);
  }

  @Test
  public void testMaxInt() {
    System.out.println(Long.MAX_VALUE);
  }


  @Test
  public void testDuration() {
    System.out.println(ZoneId.systemDefault().toString());
  }

  @Before
  public void init() {
    this.jobs = Arrays.asList("driver", "doctor", "policy", null);
  }


  private List<String> jobs;

  private String separator = "#";

}

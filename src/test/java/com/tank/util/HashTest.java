package com.tank.util;

import com.annimon.stream.Stream;
import lombok.*;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HashTest {

  @Test
  public void hashValueTest() {
    int hashRing = 32;
    List<Integer> rs = IntStream.range(1, 64).boxed()
        .map(i -> ThreadLocalRandom.current().nextInt(i))
        .map(i -> i % (hashRing - 1))
        .collect(Collectors.toList());


    System.out.println(rs);

    String[] servers = new String[]{"192.168.0.1", "192.168.11.12", "192.168.5.10"};

    List<MyTuple2<Integer, String>> tuple2s = Stream.of(servers).map(server -> {
      int index = Math.abs(Objects.hash(server)) % (hashRing - 1);
      MyTuple2<Integer, String> tp = new MyTuple2<>(index, server);
      return tp;
    }).toList();

    SortedMap<Integer, String> sortedMap = new TreeMap<Integer, String>();
    tuple2s.forEach(t -> sortedMap.putIfAbsent(t.first, t.second));
    String tips;
    for (Map.Entry<Integer, String> entry : sortedMap.entrySet()) {
      tips = String.format("index: %d, hash ip:%s", entry.getKey(), entry.getValue());
      System.out.println(tips);
    }

    //build range ring

    List<Integer> keys = Stream.of(sortedMap).map(item -> item.getKey()).toList();
    List<RingNode<Integer, Integer>> serverNodes = Stream.of(keys)
        .map(index -> new RingNode<Integer, Integer>(index, Lists.newArrayList()))
        .toList();

    int size = serverNodes.size();
    int lastIndex = size - 1;
    int nextIndex;

    List<RingNode<Integer, Integer>> lists = Lists.newArrayList();

    for (int index = 0; index < size; index++) {
      RingNode<Integer, Integer> node = serverNodes.get(index);
      nextIndex = index == lastIndex ? 0 : index + 1;
      RingNode<Integer, Integer> tmpNode = serverNodes.get(nextIndex);
      node.addItem(tmpNode.index);
      lists.add(node);
    }


    SortedMap<Integer, RingNode<Integer, Integer>> circle = new TreeMap<>();
    Map<Integer, List<Integer>> contents = new HashMap<>();
    Stream.of(lists).forEach(item -> {
      circle.putIfAbsent(item.index, item);
      contents.put(item.index, Lists.newArrayList());
    });

    for (Integer value : rs) {
      int index = findIndex(lists, value);
      contents.get(index).add(value);
    }

    System.out.println("===========================");
    for (Map.Entry<Integer, String> entry : sortedMap.entrySet()) {
      Integer key = entry.getKey();
      String values = contents.get(key).toString();
      String ip = entry.getValue();
      System.out.println(String.format("ip:%s, contents:%s", ip, values));
    }


  }


  private int findIndex(List<RingNode<Integer, Integer>> nodes, int value) {

    for (RingNode<Integer, Integer> ringNode : nodes) {
      if (value <= ringNode.getIndex()) {
        return ringNode.getIndex();
      }
    }

    return nodes.get(0).getIndex();

  }


  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  private class MyTuple2<T1, T2> {

    private T1 first;

    private T2 second;

  }

  @Setter
  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  private class RingNode<T1, T2> {

    public RingNode(@NonNull final Collection<T2> collections) {
      this.collections = collections;
    }

    private T1 index;

    private Collection<T2> collections;

    public void addItem(@NonNull final T2 item) {
      this.collections.add(item);
    }

    public T2 fetchNextNode() {
      return this.collections.iterator().next();
    }
  }

}

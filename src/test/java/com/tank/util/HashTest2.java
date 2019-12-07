package com.tank.util;

import com.annimon.stream.IntStream;
import com.annimon.stream.Stream;
import lombok.NonNull;
import lombok.val;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.util.*;

public class HashTest2 {

  @Test
  public void testHash2() {
    //this.serversHashValue().forEach(System.out::println);

    SortedMap<Integer, List<Integer>> results = this.serverRingMapping();
    val servers = this.serversHashValueMapping();

    int key = 0;
    for (Integer data : this.mockData()) {
      int hashValue = this.hashValue(data);
      val tailMap = results.tailMap(hashValue);
      key = tailMap.isEmpty() ? results.firstKey() : tailMap.firstKey();
      results.get(key).add(data);
    }

    deleteNode(917, results);

    for (Integer hashKey : results.keySet()) {
      System.out.println(String.format("server:%s, key:%d,size:[%d]",
          servers.get(hashKey),
          hashKey,
          results.get(hashKey).size())
      );
    }

  }


  private List<Integer> mockData() {
    return IntStream.range(1, 1024).boxed().toList();
  }

  private void deleteNode(@NonNull final Integer nodeHashValue, @NonNull final SortedMap<Integer, List<Integer>> result) {

    List<Integer> data = result.get(nodeHashValue);
    result.remove(nodeHashValue);
    SortedMap<Integer, List<Integer>> nextNode = result.tailMap(nodeHashValue);
    int targetKey = nextNode.firstKey();
    System.out.println(String.format("remove key:[%d], and remove key[%d] data to next key[%d]",
        nodeHashValue, nodeHashValue, targetKey));

    //被删除节点的数据加入到下一个节点
    result.get(targetKey).addAll(data);
  }


  private SortedMap<Integer, List<Integer>> serverRingMapping() {
    SortedMap<Integer, List<Integer>> maps = new TreeMap<>();
    this.serversHashValue().forEach(value -> maps.putIfAbsent(value, Lists.newArrayList()));
    return maps;
  }

  private SortedMap<Integer, String> serversHashValueMapping() {
    SortedMap<Integer, String> maps = new TreeMap<>();
    for (String server : this.servers()) {
      int index = this.hashValue(Math.abs(Objects.hash(server)));
      maps.putIfAbsent(index, server);
    }
    return maps;
  }


  private List<Integer> serversHashValue() {
    return Stream.of(this.serversHashValueMapping()).map(item -> item.getKey()).toList();
  }


  private List<String> servers() {
    List<String> basicNodes = Arrays.asList(
        "192.168.11.11",
        "192.168.11.12",
        "192.168.11.52",
        "192.168.11.41"
    );

    List<String> allNodes = Lists.newArrayList();
    for (String basicNode : basicNodes) {
      for (int i = 1; i <= 0; i++) {
        String sharding = String.format("%s#%d", basicNode, i);
        allNodes.add(sharding);
      }
    }

    allNodes.addAll(basicNodes);

    return allNodes;

  }

  private int hashValue(int value) {
    if (value < 0) {
      throw new IllegalArgumentException("value not allowed le than 0");
    }

    return value & (ringSize - 1);
  }

  private int ringSize = 1 << 10;

}

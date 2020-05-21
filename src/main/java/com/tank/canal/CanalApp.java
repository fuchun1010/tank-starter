package com.tank.canal;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.Message;
import com.annimon.stream.Stream;

import java.net.InetSocketAddress;
import java.util.function.Predicate;

/**
 * @author tank198435163.com
 */
public class CanalApp {

  public static void main(String[] args) {
    String destination = "example";
    InetSocketAddress socketAddress = new InetSocketAddress("localhost", 11111);
    CanalConnector connector = CanalConnectors.newSingleConnector(socketAddress, destination, "", "");

    int batchSize = 100;

    Predicate<Message> p1 = message1 -> message1.getId() == -1;
    Predicate<Message> p2 = message1 -> message1.getEntries().isEmpty();
    Predicate<Message>[] validators = new Predicate[]{p1, p2};

    try {
      connector.connect();
      connector.subscribe(".*\\..*");

      for (; ; ) {
        Message message = connector.getWithoutAck(batchSize);
        boolean result = Stream.of(validators).map(v -> v.test(message)).reduce(false, Boolean::logicalOr);
        if (result) {
          continue;
        }
        long batchId = message.getId();
        System.out.println("-----receive-------");
        connector.ack(batchId);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

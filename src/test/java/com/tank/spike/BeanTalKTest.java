package com.tank.spike;

import com.dinstone.beanstalkc.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

@Slf4j
public class BeanTalKTest {

  @Test
  @SneakyThrows
  public void testPc() {
    Configuration delayQueueConfig = this.createConfig();
    CountDownLatch latch = new CountDownLatch(1);
    val factory = new BeanstalkClientFactory(delayQueueConfig);
    val topic = "delayOrder";
    JobProducer jobProducer = factory.createJobProducer(topic);
    jobProducer.putJob(100, 5, 60, "pos".getBytes());
    log.info("start");
    new Thread(() -> {
      JobConsumer consumer = factory.createJobConsumer(topic);
      for (; ; ) {
        Job job = consumer.reserveJob(60);
        if (job == null) {
          try {
            Thread.sleep(1000);
          } catch (Exception e) {
            e.printStackTrace();
          }
          continue;
        }
        consumer.deleteJob(job.getId());
        log.info("end");
      }
    }).start();

    latch.await();
  }

  private Configuration createConfig() {
    Configuration config = new Configuration();
    config.setServiceHost("127.0.0.1");
    config.setServicePort(11300);
    return config;
  }


}

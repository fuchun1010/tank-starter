package com.tank.spike;

import com.tank.wheel.CustomerJob;
import com.tank.wheel.Schedual;
import com.tank.wheel.SecondWheel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
public class TimerTest {

  @Test
  public void testTimeUnit() {
    long secondSlot = TimeUnit.SECONDS.convert(1, TimeUnit.MINUTES);
    System.out.println(secondSlot);
  }

  @Test
  public void testAddCustomerJob() {
    String startDate = "2020-02-05 17:23:22";
    String endDate = "2020-02-06 23:59:59";
    CustomerJob customerJob = new CustomerJob();
    customerJob.setId(1);
    int seconds = Schedual.toSeconds(startDate, endDate).intValue();
    customerJob.setSchedual(new Schedual().toSchedualed(seconds));

    SecondWheel secondWheel = new SecondWheel(6);
    secondWheel.addCustomerJob(customerJob);
  }

  @Test
  @SneakyThrows
  public void testLoop() {
    for (int i = 0; i < 6; i++) {
      Thread.sleep(1000);
      log.info("xx");
    }
  }

  @Test
  @SneakyThrows
  public void testSecondTimeWheel() {
    CountDownLatch countDownLatch = new CountDownLatch(1);

    String startDate = "2020-02-05 17:23:22";
    String endDate = "2020-02-06 23:59:59";
    CustomerJob customerJob = new CustomerJob();
    customerJob.setId(1);
    int seconds = Schedual.toSeconds(startDate, endDate).intValue();
    customerJob.setSchedual(new Schedual().toSchedualed(seconds));


    CustomerJob customerJob2 = new CustomerJob();
    customerJob2.setId(2);
    int seconds2 = Schedual.toSeconds(startDate, endDate).intValue();
    customerJob2.setSchedual(new Schedual().toSchedualed(seconds2));

    SecondWheel secondWheel = new SecondWheel(6);
    secondWheel.checkRound();

    new Thread(secondWheel).start();

    secondWheel.addCustomerJob(customerJob);
    Thread.sleep(2000);
    secondWheel.addCustomerJob(customerJob2);

    countDownLatch.await();
  }


}

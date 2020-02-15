package com.tank.th;

import lombok.SneakyThrows;
import lombok.val;
import org.junit.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadConditionTest {

  @Test
  @SneakyThrows
  public void testConditionLockOrUnLock() {
    ConditionThread th = new ConditionThread(new ReentrantLock(true));

    new Thread(() -> th.student1(), "student1").start();
    new Thread(() -> th.student2(), "student2").start();
    new Thread(() -> th.teacherDone(), "teacher").start();
    while (true) {
      Thread.sleep(200);
    }
  }

  @Test
  public void testTime2Date() {
    val now = System.currentTimeMillis();
    val localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(now), ZoneId.systemDefault());
    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
    val result = df.format(localDateTime);
    System.out.println(result);

  }

  private static class ConditionThread {

    public ConditionThread(final ReentrantLock lock) {
      this.lock = lock;
      this.teacher = lock.newCondition();
      this.student1 = lock.newCondition();
      this.student2 = lock.newCondition();
    }

    @SneakyThrows
    public void teacherDone() {
      this.lock.lock();
      while (single != 0) {
        this.teacher.await();
      }
      System.out.println("老师叫上课");
      this.single++;
      student1.signal();
      lock.unlock();
    }

    @SneakyThrows
    public void student1() {
      lock.lock();
      while (this.single != 1) {
        student1.await();
      }
      System.out.println("学生1醒了,准备叫醒学生2");
      single++;
      student2.signal();
      lock.unlock();
    }

    @SneakyThrows
    public void student2() {
      lock.lock();
      while (this.single != 2) {
        student2.await();
      }
      System.out.println("学生2醒了");
      single = 0;
      teacher.signal();
      lock.unlock();
    }


    private Condition teacher;

    private Condition student1;

    private Condition student2;

    private int single = 0;

    private ReentrantLock lock;
  }


}

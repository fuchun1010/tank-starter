package com.tank.spike;

import org.junit.Test;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicStampedReference;

public class SingletonTest {

  @Test
  public void testSingleton() {

    DbUtil util1 = DbUtil.getInstance();
    DbUtil util2 = DbUtil.getInstance();
    System.out.println(util1.toString());
    System.out.println(util2.toString());

  }

}

class DbUtil {


  public static DbUtil getInstance() {
    while (true) {
      DbUtil util = dbUtil.getReference();
      if (Objects.nonNull(util)) {
        return util;
      }
      int stamp = dbUtil.getStamp();
      boolean isOk = dbUtil.compareAndSet(null, new DbUtil(), 0, stamp + 1);
      if (isOk) {
        return dbUtil.getReference();
      }
    }
  }

  private static AtomicStampedReference<DbUtil> dbUtil = new AtomicStampedReference(null, 0);

  private DbUtil() {

  }
}



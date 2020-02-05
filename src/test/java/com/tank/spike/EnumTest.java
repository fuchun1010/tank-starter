package com.tank.spike;

import com.tank.wheel.Schedual;
import lombok.val;
import org.junit.Test;

import java.lang.reflect.Method;

public class EnumTest {

  @Test
  public void testEnum() {
    this.loop(BillTypes.values());
  }


  @Test
  public void testTimeDiffer() {
    String startDate = "2020-02-05 17:23:22";
    String endDate = "2020-02-06 23:59:59";
    Long milliseconds = Schedual.toSeconds(startDate, endDate);
    Schedual schedual = new Schedual();
    val rs = schedual.toSchedualed(milliseconds.intValue());
    System.out.println(rs.toString());
  }

  private int convertUnit(long seconds, int unit) {
    int hour = 0;
    while (true) {
      boolean isOk = (hour + 1) * unit <= seconds ? false : true;
      if (isOk) {
        break;
      } else {
        hour++;
      }
    }
    return hour;
  }

  private <T extends Enum<T>> void loop(Enum<T>[] enums) {
    for (Enum<T> anEnum : enums) {
      for (Method method : anEnum.getClass().getDeclaredMethods()) {
        System.out.println(method.getName());
      }
    }
  }


}

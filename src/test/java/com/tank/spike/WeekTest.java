package com.tank.spike;

import lombok.NonNull;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WeekTest {

  @Test
  public void nearestWeekDay() {
    LocalDateTime localDateTime = LocalDateTime.now();
    int currentDaOfWeek = localDateTime.getDayOfWeek().getValue();

    int differ = this.remainingDayOfWeek(currentDaOfWeek);
    int skipDays = this.nextWeekDay(WeekDay.Tuesday, 2) + differ;
    LocalDateTime targetDateTime = localDateTime.plusDays(skipDays);
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
    String result = dateTimeFormatter.format(targetDateTime);
    System.out.println(result);
  }


  private int remainingDayOfWeek(@NonNull Integer currentDaOfWeek) {
    return (totalDaysOfWeek - currentDaOfWeek) % totalDaysOfWeek;
  }


  private int nextWeekDay(final WeekDay weekDay, int skipWeek) {
    if (skipWeek <= 0) {
      throw new IllegalArgumentException("skipWeek 参与不能小于0");
    }
    int skipDays = (skipWeek - 1) * totalDaysOfWeek;
    int dayOfWeek;
    switch (weekDay) {
      case Monday:
        dayOfWeek = 1;
        break;
      case Tuesday:
        dayOfWeek = 2;
        break;
      case Wednesday:
        dayOfWeek = 3;
        break;
      case Thursday:
        dayOfWeek = 4;
        break;
      case Friday:
        dayOfWeek = 5;
        break;
      case Saturday:
        dayOfWeek = 6;
        break;
      case Sunday:
        dayOfWeek = 7;
        break;
      default:
        throw new IllegalArgumentException("参数不正确");
    }
    dayOfWeek += skipDays;
    return dayOfWeek;
  }


  enum WeekDay {
    Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday
  }

  private final int totalDaysOfWeek = 7;
}

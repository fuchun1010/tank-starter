package com.tank.wheel;

import lombok.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Schedual {

  public static Long toSeconds(@NonNull final String startDate, @NonNull final String endDate) {
    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    long differ = Duration.between(LocalDateTime.from(df.parse(startDate)), LocalDateTime.from(df.parse(endDate))).toMillis();
    return TimeUnit.SECONDS.convert(differ, TimeUnit.MILLISECONDS);
  }


  public  Schedual toSchedualed(int seconds) {
    int tmpSeconds = seconds;
    this.hour = this.toTimeUnit(tmpSeconds, 3600);
    tmpSeconds = tmpSeconds - this.hour * 3600;
    this.minutes = this.toTimeUnit(tmpSeconds, 60);
    tmpSeconds = tmpSeconds - this.minutes * 60;
    this.seconds = tmpSeconds;
    return this;
  }


  private int hour;

  private int minutes;

  private int seconds;


  private int toTimeUnit(long seconds, int unit) {

    int counter = 0;
    while (true) {
      boolean isOk = (counter + 1) * unit <= seconds ? false : true;
      if (isOk) {
        break;
      } else {
        counter++;
      }
    }
    return counter;


  }


  @Override
  public String toString() {
    return String.format("hour = %d, minutes = %d, seconds = %d", this.hour, this.minutes, this.seconds);
  }
}

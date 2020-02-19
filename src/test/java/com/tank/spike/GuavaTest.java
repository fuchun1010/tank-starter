package com.tank.spike;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.val;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;

import static com.google.common.base.Joiner.on;

public class GuavaTest {

  @Test
  @SneakyThrows
  public void testJoiner() {
    String result = on(separator).skipNulls().join(this.jobs);
    @Cleanup val out = on(separator)
        .appendTo(new OutputStreamWriter(new FileOutputStream(
            new File("joiner.txt"))), this.jobs);
    out.flush();
    System.out.println(result);
  }

  @Test
  public void testSplitter() {
    String inputStr = on(this.separator).skipNulls().join(this.jobs);
    List<String> splitedResult = Splitter.on(separator).splitToList(inputStr);
    Iterator<String> iterator = Splitter.on(separator).trimResults().split(inputStr).iterator();
    while (iterator.hasNext()) {
      String str = iterator.next();
      System.out.println(str);
    }
  }

  @Test
  public void testPreConditions() {
    String[] data = new String[this.jobs.size()];
    this.jobs.toArray(data);
    Preconditions.checkElementIndex(10, data.length, "over max index of array");
  }

  @Test
  public void testBase64Encoder() {
    byte[] result = Base64.getEncoder().encode("hello".getBytes());
    Preconditions.checkArgument(result.length > 1);
    System.out.println(new String(result));
    System.out.println(new String(Base64.getDecoder().decode(result)));
  }


  @Before
  public void init() {
    this.jobs = Arrays.asList("driver", "doctor", "policy", null);
  }

  private List<String> jobs;

  private String separator = "#";

}

package com.tank.spike;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static java.lang.String.format;

@RunWith(Parameterized.class)
public class TestUnitSpike {

  @Test
  public void testMaxInteger() {
    System.out.println(Integer.MAX_VALUE);
  }


  public TestUnitSpike(Integer inputNumber,
                       Boolean expectedResult) {
    this.inputNumber = inputNumber;
    this.expectedResult = expectedResult;
  }

  @Parameterized.Parameters
  public static Collection parameters() {
    return Arrays.asList(new Object[][]{
            {2, true},
            {19, true}
        }
    );
  }

  @Before
  public void init() {
    this.primeNumberChecker = new PrimeNumberChecker();
  }

  @Test
  public void testPrimeNumberChecker() {
    System.out.println(format("inputNumber = %d", inputNumber));
    Assert.assertEquals(this.expectedResult, this.primeNumberChecker.validate(inputNumber));
  }


  private static class PrimeNumberChecker {

    public Boolean validate(final Integer primaryKey) {
      for (int i = 2; i < (primaryKey / 2); i++) {
        if (primaryKey % i == 0) {
          return false;
        }
      }
      return true;
    }
  }


  private Integer inputNumber;
  private Boolean expectedResult;
  private PrimeNumberChecker primeNumberChecker;


}

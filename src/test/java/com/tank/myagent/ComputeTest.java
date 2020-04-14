package com.tank.myagent;

import com.tank.spike.BillTypes;
import lombok.NonNull;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ComputeTest {


  @Test
  public void test2() {
    try {
      BillTypes result = this.findEnumBy(10, BillTypes.class);
      if (result != null) {
        System.out.println("xxx");
      }
    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
      e.printStackTrace();
    }
  }


  private <I extends Enum> I findEnumBy(@NonNull final Integer type, @NonNull final Class<I> clazz) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

    Method method = clazz.getDeclaredMethod("values");
    I[] results = ((I[]) method.invoke(clazz));
    //EMPTY
    I defaultResult = ((I) I.valueOf(clazz, "empty".toUpperCase()));
    for (I result : results) {
      Class tmpClazz = result.getClass();
      Method typeMethod = tmpClazz.getMethod("type");
      Integer tmpType = ((Integer) typeMethod.invoke(result));
      if (tmpType == type) {
        return result;
      }
    }
    return defaultResult;
  }

  private Map<Integer, Integer> maps = new ConcurrentHashMap<>(2 << 5);

}

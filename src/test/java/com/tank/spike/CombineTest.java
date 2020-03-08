package com.tank.spike;

import com.annimon.stream.Stream;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.SneakyThrows;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static java.lang.String.format;

public class CombineTest {

  @Test
  public void testCurrentThread() {
    CyclicBarrier cyclicBarrier = new CyclicBarrier(1, () -> System.out.println("main"));
    Thread thread = new Thread(() -> {
      try {
        TimeUnit.SECONDS.sleep(1L);
        cyclicBarrier.await();
      } catch (Exception e) {
        e.printStackTrace();
      }
      System.out.println("xx");
    });
    thread.start();
    for (; ; ) ;
  }

  @Test
  public void testCalculate() {

    CyclicBarrier cyclicBarrier = new CyclicBarrier(3, () -> System.out.println("开始统计"));
    Thread t1 = new Thread(new CalculateTask(Arrays.asList(1, 2, 3), cyclicBarrier), "cq");
    Thread t2 = new Thread(new CalculateTask(Arrays.asList(4, 5, 6), cyclicBarrier), "sc");
    Thread t3 = new Thread(new CalculateTask(Arrays.asList(10, 10, 10), cyclicBarrier), "bj");
    t1.start();
    t2.start();
    t3.start();
    for (; ; ) ;

  }

  @Test
  @SneakyThrows
  public void testCalculate2() {
    Map<String, List<Integer>> cqTask = Maps.newHashMap();
    cqTask.put("cq", Arrays.asList(1, 2, 3));

    CyclicBarrier cyclicBarrier = new CyclicBarrier(1, () -> System.out.println("开始计算"));
    AsyncCallBackTaskWithResult<Integer, Integer> asyncCallBackTaskWithResult =
        new AsyncCallBackTaskWithResult<>(cqTask, task -> {
          String key = task.entrySet().iterator().next().getKey();
          return Stream.of(task.get(key)).reduce(0, Integer::sum);
        }, cyclicBarrier);

    FutureTask<Integer> futureTask = new FutureTask<>(asyncCallBackTaskWithResult);
    Thread thread = new Thread(futureTask);
    thread.start();
    Integer result = futureTask.get();
    System.out.println(format("result =%d", result));
    for (; ; ) ;

  }

  @Test
  public void testCalculate3() {

    AsyncConverter asyncConverter = new AsyncConverter();

    //TODO add some async task

    Map<String, List<Integer>> cqTask = Maps.newHashMap();
    cqTask.put("cq", Arrays.asList(1, 2, 3));

    Map<String, List<Integer>> scTask = Maps.newHashMap();
    scTask.put("sc", Arrays.asList(4, 5, 6));

    AsyncCallBackTaskWithResult<Integer, Integer> cqCalculator = new AsyncCallBackTaskWithResult<>(cqTask, task -> {
      String key = task.entrySet().iterator().next().getKey();
      return Stream.of(task.get(key)).reduce(0, Integer::sum);
    });

    AsyncCallBackTaskWithResult<Integer, Integer> scCalculator = new AsyncCallBackTaskWithResult<>(scTask, task -> {
      String key = task.entrySet().iterator().next().getKey();
      return Stream.of(task.get(key)).reduce(0, Integer::sum);
    });

    List result = asyncConverter
        .addTask(cqCalculator)
        .addTask(scCalculator)
        .process();

    for (Object obj : result) {
      System.out.println(obj.toString());
    }

    for (; ; ) ;
  }

  private static class AsyncConverter {

    public AsyncConverter addTask(@NonNull final AsyncCallBackTaskWithResult task) {
      this.tasks.add(task);
      return this;
    }

    @SneakyThrows
    public List process() {
      int numberAsyncTask = this.tasks.size();
      this.cyclicBarrier = new CyclicBarrier(numberAsyncTask, () -> System.out.println("高级统计"));
      Stream.of(this.tasks).forEach(task -> task.setCyclicBarrier(this.cyclicBarrier));
      List<FutureTask> futureTasks = Stream.of(this.tasks).map(FutureTask::new).toList();

      Stream.of(futureTasks).map(Thread::new).forEach(Thread::start);

      for (FutureTask task : futureTasks) {
        Object result = task.get();
        this.results.add(result);
      }

      return this.results;
    }


    private List<AsyncCallBackTaskWithResult> tasks = Lists.newLinkedList();

    private List results = Lists.newLinkedList();

    private CyclicBarrier cyclicBarrier;

  }

  private static class AsyncCallBackTaskWithResult<I, V> implements Callable<V> {

    public AsyncCallBackTaskWithResult(@NonNull final Map<String, List<I>> taskUnit,
                                       @NonNull Function<Map<String, List<I>>, V> fun,
                                       final CyclicBarrier cyclicBarrier) {
      this(taskUnit);
      this.cyclicBarrier = cyclicBarrier;
      this.fun = fun;
    }

    public AsyncCallBackTaskWithResult(@NonNull final Map<String, List<I>> taskUnit,
                                       @NonNull Function<Map<String, List<I>>, V> fun) {
      this(taskUnit, fun, null);

    }


    AsyncCallBackTaskWithResult(@NonNull final Map<String, List<I>> taskUnit) {
      Preconditions.checkArgument(taskUnit.size() > 0, "task unit not allowed empty");
      this.taskUnit = taskUnit;
    }

    @Override
    @SneakyThrows
    public V call() {
      V value = this.fun.apply(this.taskUnit);
      this.cyclicBarrier.await();
      return value;
    }

    private Map<String, List<I>> taskUnit;

    @Getter
    @Setter
    private CyclicBarrier cyclicBarrier;

    private Function<Map<String, List<I>>, V> fun;

  }

  private static class CalculateTask implements Runnable {

    public CalculateTask(@NonNull final List<Integer> totalPatients, @NonNull final CyclicBarrier cyclicBarrier) {
      this.totalPatients = totalPatients;
      this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    @SneakyThrows
    public void run() {
      Integer result = Stream.of(this.totalPatients).reduce(0, Integer::sum);
      this.cyclicBarrier.await();
      System.out.println(format("%s patients = %d", Thread.currentThread().getName(), result));
    }

    private List<Integer> totalPatients;

    private CyclicBarrier cyclicBarrier;
  }

}

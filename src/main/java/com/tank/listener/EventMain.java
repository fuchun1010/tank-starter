package com.tank.listener;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.concurrent.Executors;

public class EventMain {

  public static void main(String[] args) throws IOException {
    EventBus eventBus = new EventBus();
    eventBus.register(new OrderCreatedListener());
    eventBus.post("hello, order created");
    AsyncEventBus asyncEventBus = new AsyncEventBus(Executors.newSingleThreadExecutor());
    FileSystems.getDefault().newWatchService();
  }
}

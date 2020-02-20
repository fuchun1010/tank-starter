package com.tank.monitor;

import com.google.common.eventbus.EventBus;

import java.nio.file.Path;
import java.nio.file.Paths;

public class MonitorApp {

  public static void main(String[] args) {
    EventBus eventBus = new EventBus();
    Path path = Paths.get("/Users/tank198435163.com/JavaDoctor");
    DefaultFileWatcher defaultFileWatcher = new DefaultFileWatcher(path, eventBus);
    defaultFileWatcher.startWatch();
    Runtime.getRuntime().addShutdownHook(new Thread(defaultFileWatcher::stopWatch));
  }

}

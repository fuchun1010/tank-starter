package com.tank.monitor;

import com.google.common.eventbus.EventBus;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.*;
import java.util.List;
import java.util.Objects;

@Slf4j
public class DefaultFileWatcher implements FileWatcher {

  @SneakyThrows
  public DefaultFileWatcher(@NonNull final Path path,
                            @NonNull final EventBus eventBus) {
    this.path = path;
    this.watchService = FileSystems.getDefault().newWatchService();
    this.path.register(this.watchService,
        StandardWatchEventKinds.ENTRY_CREATE,
        StandardWatchEventKinds.ENTRY_DELETE,
        StandardWatchEventKinds.ENTRY_MODIFY);
    this.eventBus = eventBus;
    this.eventBus.register(new FileListener());
  }


  @Override
  @SneakyThrows
  public void startWatch() {
    this.start = true;
    log.info("dir: [{}] has been listen", this.path.toAbsolutePath());
    while (this.start) {
      this.watchKey = this.watchService.take();

      List<WatchEvent<?>> events = this.watchKey.pollEvents();
      boolean isEmpty = Objects.isNull(events) || events.isEmpty();
      if (isEmpty) {
        continue;
      }
      for (WatchEvent<?> event : events) {
        Item item = new Item(this.path.toFile(), event.kind());
        this.eventBus.post(item);
        log.info("target file:[{}]", this.path.toFile().getAbsolutePath());
      }
      this.watchKey.reset();
    }
  }

  @Override
  @SneakyThrows
  public void stopWatch() {
    if (start) {
      return;
    }
    if (this.watchService != null) {
      this.watchService.close();
    }
  }

  private volatile boolean start = false;

  private Path path;

  private WatchService watchService;

  private WatchKey watchKey;

  private EventBus eventBus;
}

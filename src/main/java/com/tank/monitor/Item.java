package com.tank.monitor;

import com.google.common.base.MoreObjects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.nio.file.WatchEvent;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Item<T> {

  private T target;

  private WatchEvent.Kind kind;

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("desc:", this.getTarget())
        .add(" op:", this.kind.name())
        .toString();
  }
}

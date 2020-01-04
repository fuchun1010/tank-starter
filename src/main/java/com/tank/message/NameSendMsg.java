package com.tank.message;

import lombok.NonNull;

public class NameSendMsg extends Message {

  public NameSendMsg(@NonNull final String name) {
    super(MessageType.NAME_SEND);
    this.fields.add(name);
  }

  @Override
  public String content() {
    return this.fields.get(contextIndex);
  }
}

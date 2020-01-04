package com.tank.message;

import lombok.NonNull;

public class NameAcceptedMsg extends Message {

  public NameAcceptedMsg(@NonNull final String name) {
    super(MessageType.NAME_ACCEPTED);
    this.fields.add(name);
  }

  @Override
  public String content() {
    return this.fields.get(contextIndex);
  }
}

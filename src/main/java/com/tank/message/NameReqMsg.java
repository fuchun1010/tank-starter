package com.tank.message;

import lombok.NonNull;

public class NameReqMsg extends Message {

  public NameReqMsg(@NonNull final String name) {
    super(MessageType.NAME_REQUEST);
    this.fields.add(name);
  }

  @Override
  public String content() {
    return this.fields.get(contextIndex);
  }
  
}

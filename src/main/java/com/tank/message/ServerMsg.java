package com.tank.message;

import lombok.NonNull;

public class ServerMsg extends Message {

  public ServerMsg(@NonNull final String serverMessage) {
    super(MessageType.SERVER_TEXT);
    this.fields.add(serverMessage);
  }

  @Override
  public String content() {
    return this.fields.get(contextIndex);
  }

}

package com.tank.message;

import lombok.NonNull;

public class UserTextMsg extends Message {

  public UserTextMsg(@NonNull final String userText) {
    super(MessageType.USER_TEXT);
    this.fields.add(userText);
  }

  @Override
  public String content() {
    return this.fields.get(this.contextIndex);
  }


}

package com.tank.message;

import com.tank.exception.InvalidationMessageException;
import lombok.NonNull;

import java.util.Objects;

public class MessageFactory {

  public Message createFromBytes(@NonNull final byte[] bytes) {

    boolean isNotOk = Objects.isNull(bytes) || bytes.length == 0;

    if (isNotOk) {
      throw new InvalidationMessageException();
    }

    int bytesLen = bytes.length;

    int lastByte = bytes[bytesLen - 1];

    isNotOk = lastByte != Message.MESSAGE_END;

    if (isNotOk) {
      throw new InvalidationMessageException();
    }

    String headerAndBody = new String(bytes, 0, bytesLen - 1, Message.charset);
    String[] items = headerAndBody.split(Character.toString((char) Message.MESSAGE_DELIMITER), -1);

    isNotOk = Objects.isNull(items) || items.length != 2;

    if (isNotOk) {
      throw new InvalidationMessageException();
    }

    String messageTypeStr = items[0];

    String body = items[1];

    MessageType messageType = MessageType.valueOf(messageTypeStr);

    switch (messageType) {
      case NAME_ACCEPTED:
        return new NameAcceptedMsg(body);
      case NAME_SEND:
        return new NameSendMsg(body);
      case NAME_REQUEST:
        return new NameReqMsg(body);
      case DISCONNECT:
        return new DisConnectionMsg();
      case USER_TEXT:
        return new UserTextMsg(body);
      case SERVER_TEXT:
        return new ServerMsg(body);
      default:
        throw new IllegalArgumentException();
    }


  }

}

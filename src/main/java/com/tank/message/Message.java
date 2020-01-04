package com.tank.message;

import lombok.NonNull;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tank198435163.com
 */
public abstract class Message {

  public Message(@NonNull final MessageType messageType) {
    this.messageType = messageType;
    this.fields = new ArrayList<>();
    this.fields.add(this.messageType.toString());
  }

  public MessageType fetchMessageType() {
    return this.messageType;
  }

  public byte[] getBytes() {
    String separator = Character.toString((char) MESSAGE_DELIMITER);
    String end = Character.toString((char) MESSAGE_END);
    String message = String.join(separator, this.fields) + end;
    return message.getBytes(charset);
  }

  public final static byte MESSAGE_DELIMITER = 0x1F;

  public final static byte MESSAGE_END = 0x1E;

  public final static Charset charset = Charset.forName("UTF-8");

  public String content() {
    return null;
  }

  protected List<String> fields;

  protected final int contextIndex = 2 >> 1;

  private MessageType messageType;
}

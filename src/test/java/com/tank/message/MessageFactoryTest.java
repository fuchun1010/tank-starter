package com.tank.message;

import org.junit.Before;
import org.junit.Test;

import java.nio.ByteBuffer;

public class MessageFactoryTest {

  @Test
  public void createFromBytes() {
    String header = MessageType.USER_TEXT.toString();
    String delimiter = Character.toString((char) Message.MESSAGE_DELIMITER);
    String body = "I am tank";
    String end = Character.toString((char) Message.MESSAGE_END);
    ByteBuffer byteBuffer = ByteBuffer.allocate(header.length() + delimiter.length() + body.length() + end.length());
    byteBuffer.put(header.getBytes(Message.charset));
    byteBuffer.put(delimiter.getBytes(Message.charset));
    byteBuffer.put(body.getBytes(Message.charset));
    byteBuffer.put(end.getBytes(Message.charset));
    byte[] msgPackage = byteBuffer.array();
    Message message = this.messageFactory.createFromBytes(msgPackage);
    System.out.println(message);
  }

  @Before
  public void setUp() {
    this.messageFactory = new MessageFactory();
  }

  private MessageFactory messageFactory;
}
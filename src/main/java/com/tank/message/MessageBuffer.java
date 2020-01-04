package com.tank.message;

public interface MessageBuffer {

  void put(byte[] data);

  byte[] getNextMessage();

}

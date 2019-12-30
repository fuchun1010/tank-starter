package com.tank.util.myio;

import lombok.Cleanup;
import lombok.SneakyThrows;
import org.junit.Test;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

public class ZeroCopy {

  @Test
  @SneakyThrows
  public void readFileWithZeroCopy() {
    File file = fetchFile();
    RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
    FileChannel fileChannel = randomAccessFile.getChannel();
    MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, fileChannel.size());
    String content = Charset.forName("UTF-8").decode(mappedByteBuffer).toString();
    System.out.println(content);
  }

  @Test
  @SneakyThrows
  public void readFileWithBytes() {
    File file = this.fetchFile();
    ByteBuffer byteBuffer = ByteBuffer.allocate(8);
    RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
    @Cleanup FileChannel channel = randomAccessFile.getChannel();

    while (true) {
      int n = channel.read(byteBuffer);
      if (n == -1) {
        break;
      }
      byteBuffer.flip();
      System.out.println(byteBuffer.hasRemaining());
      byteBuffer.clear();
    }

    System.out.println("xx");
  }

  private File fetchFile() throws URISyntaxException {
    return new File(ZeroCopy.class.getClassLoader().getResource("1.txt").toURI());
  }

}

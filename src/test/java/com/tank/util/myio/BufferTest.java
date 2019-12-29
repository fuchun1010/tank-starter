package com.tank.util.myio;

import com.annimon.stream.Stream;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URISyntaxException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Objects;

public class BufferTest {

  @Test
  public void testBreakLine() {
    String separator = System.getProperty("line.separator");
    System.out.println(separator);
  }

  @Test
  @SneakyThrows
  public void writeFileWithBuffer() {
    String words = "hello,fuchun,welcome to ShenZhen,cloud you talk about you last years when you are child";
    ByteBuffer target = ByteBuffer.wrap(words.getBytes());
    File file = targetFile();
    if (!file.exists()) {
      file.createNewFile();
    }
    FileChannel fileChannel = new FileOutputStream(file).getChannel();
    fileChannel.write(target);
    fileChannel.close();
  }

  @Test
  @SneakyThrows
  public void readFileWithBuffer() {
    File file = this.targetFile();
    FileChannel fileChannel = new FileInputStream(file).getChannel();
    ByteBuffer data = ByteBuffer.allocate(8);
    byte[] content;
    byte[] target = null;
    while (true) {
      int rs = fileChannel.read(data);
      if (rs == -1) {
        break;
      }
      int position = data.position();
      data.flip();
      content = new byte[position];
      data.get(content);
      target = Objects.isNull(target) ? content : this.concat(target, content);
      data.compact();
      data.clear();

    }
    String words = new String(ByteBuffer.wrap(target).array());
    System.out.println(words);
  }

  private byte[] concat(@NonNull final byte[]... arr) {
    int capacity = Stream.of(arr).map(e -> e.length).reduce(0, Integer::sum);
    if (capacity <= 0) {
      throw new IllegalArgumentException("字节数组长度异常");
    }
    byte[] target = new byte[capacity];
    int start = 0;
    for (byte[] bytes : arr) {
      System.arraycopy(bytes, 0, target, start, bytes.length);
      start += bytes.length;
    }

    return target;
  }

  private File targetFile() throws URISyntaxException {
    return new File(this.getClass().getClassLoader().getResource("1.txt").toURI());
  }

  private void printBuffer(@NonNull final Buffer buffer) {
    System.out.println(String.format("position:%d", buffer.position()));
    System.out.println(String.format("limit:%d", buffer.limit()));
    System.out.println(String.format("capacity:%d", buffer.capacity()));
    System.out.println(String.format("remaining:%d", buffer.remaining()));
  }
}

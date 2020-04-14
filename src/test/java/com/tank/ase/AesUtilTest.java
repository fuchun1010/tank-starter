package com.tank.ase;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AesUtilTest {

  public static final String ALGORITHM_AES_CBC_PKCS5PADDING = "AES/CBC/PKCS5Padding";

  public static final String ALGORITHM_AES = "AES";

  @Test
  public void testEncrypt() throws Exception {
    System.out.println("MaxAllowedKeyLength=[" + "k3pdo6tfmjh0bwf8".length() + "].");
    String result = encrypt("123", "k3pdo6tfmjh0bwf8");
    System.out.println(result);
  }

  public static String encrypt(String src, String key) throws Exception {
    String encodingFormat = "UTF-8";
    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    byte[] raw = key.getBytes();
    SecretKeySpec secretKeySpec = new SecretKeySpec(raw, "AES");
    cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
    byte[] encrypted = cipher.doFinal(src.getBytes(encodingFormat));
    return Base64.encodeBase64String(encrypted);
  }

}

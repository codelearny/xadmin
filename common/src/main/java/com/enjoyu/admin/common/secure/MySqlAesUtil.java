package com.enjoyu.admin.common.secure;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public  class  MySqlAesUtil {

    public synchronized static String aesEncrypt(String content, String keyStr) {
        try {
            SecretKey k = key(keyStr);
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, k);
            byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
            byte[] cipherBytes = cipher.doFinal(bytes);
            return Hex.encodeHexString(cipherBytes, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String aesDecrypt(String content, String keyStr) {
        try {
            SecretKey k = key(keyStr);
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, k);
            byte[] bytes = Hex.decodeHex(content);
            byte[] cipherBytes = cipher.doFinal(bytes);
            return new String(cipherBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static SecretKey key(String keyStr) {
        byte[] keyBytes = new byte[16];
        int i = 0;
        for (byte b : keyStr.getBytes(StandardCharsets.UTF_8)) {
            keyBytes[i++ % 16] ^= b;
        }
        return new SecretKeySpec(keyBytes, "AES");
    }
}

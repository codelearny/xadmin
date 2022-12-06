package com.enjoyu.admin.common.secure;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class PBEUtilTest {

    @Test
    public void test() throws Exception {
        String message = "高阁客竟去，小园花乱飞。参差连曲陌，迢递送斜晖。肠断未忍扫，眼穿仍欲归。芳心向春尽，所得是沾衣。";
        String password = "123456XX";
        System.out.printf("Message: %s%n", message);
        byte[] salt = PBEUtil.randomSalt(11);
        byte[] iv = PBEUtil.randomSalt(16);
        System.out.printf("Salt: %s%n", Arrays.toString(salt));
        System.out.printf("IV: %s%n", Arrays.toString(iv));
        byte[] encrypt = PBEUtil.encrypt(message.getBytes(), password, salt, iv);
        System.out.printf("Encrypted: %s%n", EncodeUtil.bytes2Hex(encrypt));
        byte[] decrypted = PBEUtil.decrypt(encrypt, password, salt, iv);
        String decryptedText = new String(decrypted);
        System.out.printf("Decrypted: %s%n", decryptedText);
        Assertions.assertEquals(message, decryptedText);
    }

}

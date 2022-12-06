package com.enjoyu.admin.common.secure;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Base64;

public class AESUtilTest {

    @Test
    public void test() throws Exception {
        String message = "夫子何为者，栖栖一代中。地犹鄹氏邑，宅即鲁王宫。叹凤嗟身否，伤麟怨道穷。今看两楹奠，当与梦时同。";
        String password = "123456XX";
        byte[] iv = PBEUtil.randomSalt(16);
        System.out.printf("Message: %s%n", message);
        byte[] encrypt = AESUtil.encrypt(message.getBytes(), password.getBytes(), iv);
        System.out.printf("Encrypted: %s%n", Base64.getEncoder().encodeToString(encrypt));
        byte[] decrypted = AESUtil.decrypt(encrypt, password.getBytes(), iv);
        String decryptedText = new String(decrypted);
        System.out.printf("Decrypted: %s%n", decryptedText);
        Assertions.assertEquals(message, decryptedText);
    }

}
package com.enjoyu.admin.common.secure;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

class MySqlAesUtilTest {

    @Test
    void testAesEncrypt() {
        String keyStr = "1234567890123456";
        System.out.println(Arrays.toString("abc".getBytes(StandardCharsets.UTF_8)));
        System.out.println(Hex.encodeHexString("你好".getBytes(StandardCharsets.UTF_8)));
        String encrypt = MySqlAesUtil.aesEncrypt("你好", keyStr);
        System.out.println(encrypt);
        String decrypt = MySqlAesUtil.aesDecrypt(encrypt, keyStr);
        System.out.println(decrypt);
    }
    @Test
    void t(){

        System.out.println(-5%4);

    }

}

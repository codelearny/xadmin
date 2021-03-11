package com.enjoyu.admin.common.secure;

import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class HmacUtilTest {

    @Test
    public void testHmacMD5() throws InvalidKeyException, NoSuchAlgorithmException {
        String message = "山暝听猿愁，沧江急夜流。\n风鸣两岸叶，月照一孤舟。\n建德非吾土，维扬忆旧游。\n还将两行泪，遥寄海西头。";
        System.out.printf("message %n%s%n", message);
        byte[] hash = HmacUtil.hash("abcdefg", message, HmacUtil.HMAC_MD5);
        System.out.println(EncodeUtil.bytesToHexString(hash));
    }

    @Test
    public void testHmacSHA256() throws InvalidKeyException, NoSuchAlgorithmException {
        String message = "荒戍落黄叶，浩然离故关。\n高风汉阳渡，初日郢门山。\n江上几人在，天涯孤棹还。\n何当重相见，樽酒慰离颜。";
        System.out.printf("message %n%s%n", message);
        byte[] hash = HmacUtil.hash(HmacUtil.genKey(HmacUtil.HMAC_SHA256), message.getBytes(), HmacUtil.HMAC_SHA256);
        System.out.println(EncodeUtil.bytesToHexString(hash));
    }

    @Test
    public void testHmacSHA512() throws InvalidKeyException, NoSuchAlgorithmException {
        String message = "海上生明月，天涯共此时。\n情人怨遥夜，竟夕起相思。\n灭烛怜光满，披衣觉露滋。\n不堪盈手赠，还寝梦佳期。";
        System.out.printf("message %n%s%n", message);
        byte[] hash = HmacUtil.hash(HmacUtil.HMAC_SHA512, message, HmacUtil.HMAC_SHA512);
        System.out.println(EncodeUtil.bytesToHexString(hash));
    }

    @Test
    public void testKeyGen() throws NoSuchAlgorithmException {
        Key key = HmacUtil.genKey(HmacUtil.HMAC_MD5);
        System.out.println(key);
    }

    @Test
    public void hmacSHA256Sign() throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
        long timestamp = System.currentTimeMillis();
        System.out.printf("timestamp: %s%n", timestamp);
        String secret = "cicEricssonCuhq";
        String message = timestamp + "/n" + secret;
        System.out.printf("message: %s%n", message);
        Key key = HmacUtil.genKey(HmacUtil.HMAC_SHA256, secret.getBytes(StandardCharsets.UTF_8));
        byte[] hash = HmacUtil.hash(key, message.getBytes(StandardCharsets.UTF_8), HmacUtil.HMAC_SHA256);
        String base64Encode = EncodeUtil.base64Encode(hash);
        System.out.printf("base64Encode: %s%n", base64Encode);
        String urlEncode = EncodeUtil.urlEncode(base64Encode, StandardCharsets.UTF_8);
        System.out.printf("urlEncode: %s%n", urlEncode);
    }

}

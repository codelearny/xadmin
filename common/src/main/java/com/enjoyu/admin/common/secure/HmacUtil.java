package com.enjoyu.admin.common.secure;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * HMAC是密钥相关的哈希运算消息认证码
 * Keyed-hash Message Authentication Code
 * Hash-based Message Authentication Code(RFC2104)
 */
public abstract class HmacUtil {

    public static final String HMAC_MD5 = "HmacMD5";
    public static final String HMAC_SHA256 = "HmacSHA256";
    public static final String HMAC_SHA512 = "HmacSHA512";

    public static byte[] hash(String key, String message, String algorithm) throws NoSuchAlgorithmException, InvalidKeyException {
        return hash(genKey(algorithm, key.getBytes(StandardCharsets.UTF_8)), message.getBytes(StandardCharsets.UTF_8), algorithm);
    }

    public static byte[] hash(Key key, byte[] message, String algorithm) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac hmac = Mac.getInstance(algorithm);
        hmac.init(key);
        hmac.update(message);
        return hmac.doFinal();
    }

    public static Key genKey(String algorithm) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
        return keyGenerator.generateKey();
    }

    public static Key genKey(String algorithm, byte[] secret) {
        return new SecretKeySpec(secret, algorithm);
    }
}

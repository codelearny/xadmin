package com.enjoyu.admin.common.secure;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.security.*;
import java.security.spec.InvalidKeySpecException;

/**
 * Password Base Encryption<br>
 * 基于口令加密<br>
 * 采用随机数（这里我们叫做盐）杂凑多重加密等方法保证数据的安全性<br>
 * PBEWithHmacSHA256AndAES_128算法内部使用CBC模式AES，需要提供iv(16bytes)
 *
 * @author enjoyu
 */
public abstract class PBEUtil {
    private static final String PBEWITH_HMAC_SHA_256_AND_AES_128 = "PBEWithHmacSHA256AndAES_128";
    private static final int SALT_COUNT = 1000;

    public static byte[] encrypt(byte[] input, String password, byte[] salt, byte[] iv) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Key key = genKey(password);
        PBEParameterSpec spec = new PBEParameterSpec(salt, SALT_COUNT, new IvParameterSpec(iv));
        Cipher cipher = Cipher.getInstance(key.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, key, spec);
        return cipher.doFinal(input);
    }

    public static byte[] decrypt(byte[] input, String password, byte[] salt, byte[] iv) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Key key = genKey(password);
        PBEParameterSpec spec = new PBEParameterSpec(salt, SALT_COUNT, new IvParameterSpec(iv));
        Cipher cipher = Cipher.getInstance(key.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, key, spec);
        return cipher.doFinal(input);
    }

    /**
     * 随机生成盐
     *
     * @param num 长度
     * @return salt
     */
    public static byte[] randomSalt(int num) throws NoSuchAlgorithmException {
        return SecureRandom.getInstanceStrong().generateSeed(num);
    }

    private static Key genKey(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(PBEWITH_HMAC_SHA_256_AND_AES_128);
        return keyFactory.generateSecret(keySpec);
    }
}

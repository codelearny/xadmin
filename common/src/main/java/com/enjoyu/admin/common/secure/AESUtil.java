package com.enjoyu.admin.common.secure;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * 高级加密标准（英语：Advanced Encryption Standard，缩写：AES），是一种区块加密标准。
 * 可以使用128bit位、192、和256位密钥
 * 使用CBC模式，需要一个初始向量iv，可增加加密算法的强度
 * 填充方式为NoPadding时，最后一个块的填充内容由程序员确定，通常为0.
 * 填充方式为Pkcs5Padding时，最后一个块需要填充χ个字节，填充的值就是χ，也就是填充内容由JDK确定
 */
public abstract class AESUtil {
    private static final int bits = 256 >> 3;
    private static final String AES = "AES";
    private static final String AES_CBC_PKCS5_PADDING = "AES/CBC/PKCS5Padding";

    /**
     * 加密
     *
     * @param message  明文
     * @param password 密钥
     * @param iv       iv
     * @return 密文
     */
    public static byte[] encrypt(byte[] message, byte[] password, byte[] iv) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        SecretKeySpec key = genKey(password);
        Cipher cipher = Cipher.getInstance(AES_CBC_PKCS5_PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
        return cipher.doFinal(message);
    }

    /**
     * 解密
     *
     * @param ciphertext 密文
     * @param password   密钥
     * @return 明文
     */
    public static byte[] decrypt(byte[] ciphertext, byte[] password, byte[] iv) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        SecretKeySpec key = genKey(password);
        Cipher cipher = Cipher.getInstance(AES_CBC_PKCS5_PADDING);
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
        return cipher.doFinal(ciphertext);
    }

    /**
     * CBC模式需要16bytes的矢量
     *
     * @return iv
     */
    public static byte[] genIV() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstanceStrong();
        return sr.generateSeed(16);
    }

    /**
     * 密钥支持 128，192，256位
     *
     * @param key 密钥
     * @return SecretKeySpec
     */
    private static SecretKeySpec genKey(byte[] key) {
        byte[] arr = new byte[bits];
        System.arraycopy(key, 0, arr, 0, Math.min(key.length, arr.length));
        return new SecretKeySpec(arr, AES);
    }

}
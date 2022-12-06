package com.enjoyu.admin.common.secure;

import com.sun.crypto.provider.SunJCE;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * 算法提供方 {@link SunJCE}
 * <p>
 * DES是一种对称加密算法，加密和解密使用同样的密钥，全称为 Data Encryption Standard
 * <p>
 * DES是一种典型的分组密码，将数据按照8个字节一段进行加密或解密，从而得到一段8个字节的密文或者明文。再按照顺序将计算所得的数据连在一起。DES加密解密时要求数据长度必须为8个字节的倍数，因此当数据长度不足时必须先进行数据填充，这里使用的填充算法根据系统的不同可能略有不同。
 * 对DES而言，块长度为64位。同时，DES使用密钥来自定义变换过程，因此算法认为只有持有加密所用的密钥的用户才能解密密文。
 * 密钥表面上是64位的，然而只有其中的56位被实际用于算法，其余8位可以被用于奇偶校验，并在算法中被丢弃。因此，DES的有效密钥长度仅为56位。
 * <p>
 * 3DES数据加密算法是一种可逆的对称加密算法，也称三重数据加密算法（英语：Triple Data Encryption Algorithm，缩写为TDEA，Triple DEA），或称3DES（Triple DES），它是一种为了替代原先DES而建立的数据加密标准。
 * 3DES块加密算法的设计用来提供一种相对简单的方法，即通过增加DES的密钥长度来避免类似的攻击，而不是设计一种全新的密码算法。目前3DES作为DES的过渡算法已经逐渐被更安全的AES代替。
 * <p>
 * 3DES的填充模式
 * 块密码只能对确定长度的数据块进行处理，而消息的长度通常是可变的，因此需要选择填充模式。
 * 填充区别：在ECB、CBC工作模式下最后一块要在加密前进行填充，其它不用选择填充模式；
 * 填充模式：3DES支持的填充模式为PKCS5、PKCS7和NONE不填充。
 * <p>
 * 3DES密钥KEY和初始化向量IV
 * 初始化向量IV可以有效提升安全性，但是在实际的使用场景中，它不能像密钥KEY那样直接保存在配置文件或固定写死在代码中，一般正确的处理方式为：在加密端将IV设置为一个8位的随机值，然后和加密文本一起返给解密端即可。
 * <p>
 * 区块长度：3DES规定区块长度只有一个值，固定为64Bit，对应的字节为8位；
 * 密钥长度：3DES规定密钥长度只有两个值，128Bit、192Bit，对应的字节为16位和24位；
 * 密钥KEY：该字段不能公开传输，用于加密和解密数据；
 * 初始化向量IV：该字段可以公开，用于将加密随机化。同样的明文被多次加密也会产生不同的密文，避免了较慢的重新产生密钥的过程，初始化向量与密钥相比有不同的安全性需求，因此IV通常无须保密。然而在大多数情况中，不应当在使用同一密钥的情况下两次使用同一个IV，在3DES算法中一般推荐初始化向量IV为8位的随机值。
 *
 * @author enjoyu
 */
public abstract class DESUtil {
    public static final Charset CHARSET = StandardCharsets.UTF_8;
    public static final String DES = "DES";
    public static final String DES3 = "DESede";
    private static final int DES_BITS = 64;
    private static final int DES_BYTES = DES_BITS >> 3;
    private static final int DES3_BITS = 192;
    private static final int DES3_BYTES = DES3_BITS >> 3;

    /**
     * 加解密方式+工作模式+填充方式
     */
    public static final String DES_ECB_PKCS5PADDING = "DES/ECB/PKCS5Padding";
    public static final String DES_CBC_PKCS5PADDING = "DES/CBC/PKCS5Padding";
    public static final String DESEDE_ECB_PKCS5PADDING = "DESede/ECB/PKCS5Padding";
    public static final String DESEDE_CBC_PKCS5PADDING = "DESede/CBC/PKCS5Padding";

    /**
     * 支持8字节的密钥
     */
    public static Key desKey(byte[] k) throws InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException {
        byte[] arr = new byte[DES_BYTES];
        System.arraycopy(k, 0, arr, 0, Math.min(k.length, arr.length));
        DESKeySpec desKeySpec = new DESKeySpec(arr);
        SecretKeyFactory instance = SecretKeyFactory.getInstance(DES);
        return instance.generateSecret(desKeySpec);
    }

    /**
     * 支持24字节的密钥
     */
    public static Key des3Key(byte[] k) throws InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException {
        byte[] arr = new byte[DES3_BYTES];
        System.arraycopy(k, 0, arr, 0, Math.min(k.length, arr.length));
        DESedeKeySpec desKeySpec = new DESedeKeySpec(arr);
        SecretKeyFactory instance = SecretKeyFactory.getInstance(DES3);
        return instance.generateSecret(desKeySpec);
    }

    /**
     * 生成iv 支持8字节
     */
    public static IvParameterSpec iv(byte[] k) throws NoSuchAlgorithmException {
        byte[] arr = new byte[DES_BYTES];
        System.arraycopy(k, 0, arr, 0, Math.min(k.length, arr.length));
        return new IvParameterSpec(arr);
    }

    public static byte[] desEcbEncrypt(byte[] data, byte[] password) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException {
        return encrypt(data, desKey(password), null, DES_ECB_PKCS5PADDING);
    }

    public static byte[] desEcbDecrypt(byte[] sec, byte[] password) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException {
        return decrypt(sec, desKey(password), null, DES_ECB_PKCS5PADDING);
    }

    public static byte[] desCbcEncrypt(byte[] data, byte[] password) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException {
        return encrypt(data, desKey(password), iv(password), DES_CBC_PKCS5PADDING);
    }

    public static byte[] desCbcDecrypt(byte[] sec, byte[] password) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException {
        return decrypt(sec, desKey(password), iv(password), DES_CBC_PKCS5PADDING);
    }

    public static byte[] des3EcbEncrypt(byte[] data, byte[] password) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException {
        return encrypt(data, des3Key(password), null, DESEDE_ECB_PKCS5PADDING);
    }

    public static byte[] des3EcbDecrypt(byte[] sec, byte[] password) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException {
        return decrypt(sec, des3Key(password), null, DESEDE_ECB_PKCS5PADDING);
    }

    public static byte[] des3CbcEncrypt(byte[] data, byte[] password) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException {
        return encrypt(data, des3Key(password), iv(password), DESEDE_CBC_PKCS5PADDING);
    }

    public static byte[] des3CbcDecrypt(byte[] sec, byte[] password) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException {
        return decrypt(sec, des3Key(password), iv(password), DESEDE_CBC_PKCS5PADDING);
    }

    public static byte[] des3CbcEncrypt(byte[] data, Key key, IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException {
        return encrypt(data, key, iv, DESEDE_CBC_PKCS5PADDING);
    }

    public static byte[] des3CbcDecrypt(byte[] sec, Key key, IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException {
        return decrypt(sec, key, iv, DESEDE_CBC_PKCS5PADDING);
    }

    public static byte[] encrypt(byte[] bytes, Key key, IvParameterSpec iv, String algorithm) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        return cipher(bytes, algorithm, key, iv, Cipher.ENCRYPT_MODE);
    }

    public static byte[] decrypt(byte[] bytes, Key key, IvParameterSpec iv, String algorithm) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        return cipher(bytes, algorithm, key, iv, Cipher.DECRYPT_MODE);
    }

    public static byte[] cipher(byte[] bytes, String algorithm, Key key, IvParameterSpec iv, int mode) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        Cipher cipher = Cipher.getInstance(algorithm);
        if (iv == null) {
            cipher.init(mode, key);
        } else {
            cipher.init(mode, key, iv);
        }
        return cipher.doFinal(bytes);
    }
}

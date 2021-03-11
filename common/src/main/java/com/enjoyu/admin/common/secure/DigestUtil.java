package com.enjoyu.admin.common.secure;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 信息摘要工具类
 * <p> MessageDigest 类为应用程序提供信息摘要算法的功能，如 SHA-1 或 SHA-256 算法。
 * 信息摘要是安全的单向哈希函数，它接收任意大小的数据，并输出固定长度的哈希值。
 * MessageDigest 对象开始被初始化。该对象通过使用 update（）方法处理数据。
 * 任何时候都可以调用 reset（）方法重置摘要。一旦所有需要更新的数据都已经被更新了，应该调用digest() 方法之一完成哈希计算。
 * 对于给定数量的更新数据，digest 方法只能被调用一次。在调用 digest 之后，MessageDigest 对象被重新设置成其初始状态。
 * </p>
 * java8 提供的摘要算法 https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#MessageDigest
 */
public abstract class DigestUtil {

    public static final String MD5 = "MD5";
    public static final String SHA_1 = "SHA-1";
    public static final String SHA_256 = "SHA-256";

    /**
     * 生成摘要
     *
     * @param message   消息体
     * @param algorithm 算法
     * @return 摘要字节数组
     * @throws NoSuchAlgorithmException 错误的摘要算法
     */
    public static byte[] digest(String message, String algorithm) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance(algorithm);
        return md5.digest(message.getBytes(StandardCharsets.UTF_8));
    }

}

package com.enjoyu.admin.common.secure;

import com.google.common.primitives.Bytes;
import org.springframework.util.FileCopyUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

/**
 * openssl enc -des3 -e -a -p -md md5 -k '$Wj5!(zz' -in aaa.log -out aaa.log.des3
 * -a 生成结果使用base64编码
 * -p 显示生成的key和iv
 */
public class OpenSslDes3Util {
    public static final String SALT_TAG = "Salted__";
    public static final byte[] SALT_TAG_BYTES = SALT_TAG.getBytes(StandardCharsets.US_ASCII);
    public static final int SALT_TAG_END = SALT_TAG_BYTES.length;
    public static final int SALT_LENGTH = 8;
    public static final int SALT_END = SALT_TAG_END + SALT_LENGTH;
    public static final int KEY_LENGTH = 24;
    public static final int IV_LENGTH = 8;

    public static byte[] encode(File file, String password) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException, InvalidKeyException, IOException {
        byte[] fileBytes = FileCopyUtils.copyToByteArray(file);
        byte[] saltBytes = PBEUtil.randomSalt(SALT_LENGTH);
        byte[] passwordBytes = password.getBytes(StandardCharsets.US_ASCII);
        byte[] keyAndIv = getKeyAndIv(passwordBytes, saltBytes);
        SecretKeySpec keySpec = new SecretKeySpec(keyAndIv, 0, KEY_LENGTH, DESUtil.DES3);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(keyAndIv, KEY_LENGTH, IV_LENGTH);
        byte[] bytes = DESUtil.des3CbcEncrypt(fileBytes, keySpec, ivParameterSpec);
        return Bytes.concat(SALT_TAG_BYTES, saltBytes, bytes);
    }

    public static byte[] decode(File des3File, String password) throws IOException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException, InvalidKeyException {
        byte[] passwordBytes = password.getBytes(StandardCharsets.US_ASCII);
        byte[] fileBytes = FileCopyUtils.copyToByteArray(des3File);
        byte[] saltBytes = new byte[0];
        byte[] headOfFile = Arrays.copyOfRange(fileBytes, 0, SALT_TAG_END);
        if (Arrays.equals(SALT_TAG_BYTES, headOfFile)) {
            saltBytes = Arrays.copyOfRange(fileBytes, SALT_TAG_END, SALT_END);
            fileBytes = Arrays.copyOfRange(fileBytes, SALT_END, fileBytes.length);
        }
        byte[] keyAndIv = getKeyAndIv(passwordBytes, saltBytes);
        SecretKeySpec keySpec = new SecretKeySpec(keyAndIv, 0, KEY_LENGTH, DESUtil.DES3);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(keyAndIv, KEY_LENGTH, IV_LENGTH);
        return DESUtil.des3CbcDecrypt(fileBytes, keySpec, ivParameterSpec);
    }

    private static byte[] getKeyAndIv(byte[] passwordBytes, byte[] saltBytes) throws NoSuchAlgorithmException {
        MessageDigest md5 = DigestUtil.md5();
        byte[] concat = Bytes.concat(passwordBytes, saltBytes);
        byte[] hash = new byte[0];
        byte[] keyAndIv = new byte[0];
        for (int i = 0; i < 2; i++) {
            byte[] tmp = Bytes.concat(hash, concat);
            hash = md5.digest(tmp);
            keyAndIv = Bytes.concat(keyAndIv, hash);
        }
        return keyAndIv;
    }

}
